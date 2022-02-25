package com.hmellema.vertxutils.eventbus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import java.io.IOException;

import io.vertx.core.json.jackson.DatabindCodec;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/** A simple Codec for using a generic Pojo for Vert.x event bus
 * Uses Jackson to serialize/deserialize to/from wire format
 *
 * Based on GenericCodec from:
 * https://levidoro.medium.com/vert-x-event-bus-send-any-object-with-generic-codec-t-a0bc1feab13a
 *
 */
@Slf4j
@RequiredArgsConstructor
public class GenericPojoCodec<MessagePojoT>
  implements MessageCodec<MessagePojoT, MessagePojoT> {

  private static final ObjectMapper objectMapper = DatabindCodec.prettyMapper();

  @NonNull
  private final Class<MessagePojoT> cls;

  @Override
  public void encodeToWire(Buffer buffer, MessagePojoT message) {
    try {
      byte[] messageBytes = objectMapper.writeValueAsBytes(message);
      buffer.appendInt(messageBytes.length);
      buffer.appendBytes(messageBytes);
    } catch (JsonProcessingException exc) {
      log.error("Failed to write message {}", message, exc);
      throw new RuntimeException(exc);
    }
    
  }

  @Override
  public MessagePojoT decodeFromWire(final int pos, final Buffer buffer) {
    // local copy
    int posLocal = pos;

    // Length of Message
    int length = buffer.getInt(posLocal);

    // Jump 4 because getInt() == 4 bytes
    posLocal += 4;

    // Extract byte array from buffer
    byte[] messageBytes = buffer.getBytes(posLocal, posLocal + length);

    try {
      return objectMapper.readValue(messageBytes, cls);
    } catch (IOException exc) {
      log.error("Could not read message", exc);
      throw new RuntimeException(exc);
    }
  }

  @Override
  public MessagePojoT transform(final MessagePojoT message) {
    // If a message is sent *locally* across the event bus.
    // This example sends message just as is
    return message;
  }

  @Override
  public String name() {
    // Each codec must have a unique name.
    return cls.getSimpleName() + "Codec";
  }

  @Override
  public byte systemCodecID() {
    return -1;
  }
}
