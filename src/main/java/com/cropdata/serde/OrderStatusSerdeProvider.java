package com.cropdata.serde;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.cropdata.value.OrderStatusVo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class OrderStatusSerdeProvider extends SimpleModule {

    /**
	 *  
	 */
	private static final long serialVersionUID = 1L;

	public OrderStatusSerdeProvider() {
        addSerializer(OrderStatusVo.class, new OrderStatusVoSerializer());
        addDeserializer(OrderStatusVo.class, new OrderStatusVoDeserializer());
    }

    private static class OrderStatusVoSerializer extends JsonSerializer<OrderStatusVo> {
        @Override
        public void serialize(OrderStatusVo orderStatusVo, JsonGenerator jsonGenerator,
                SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(orderStatusVo.getValue().getValue());
        }
    }

    private static class OrderStatusVoDeserializer extends JsonDeserializer<OrderStatusVo> {
        @Override
        public OrderStatusVo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String value = jsonParser.getValueAsString();
            return new OrderStatusVo(value);
        }
    }
}


//	//@Override
//	public OrderStatusVo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
//        String value = jsonParser.getValueAsString();
//        return new OrderStatusVo(value);
//    }
	
	
	
//	@Override
//	public JsonDeserializer<OrderStatusVo> getJsonDeserializer() {
//		 return new JsonDeserializer<>() {
//	            @Override
//	            public OrderStatusVo deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//	                final var value = p.getValueAsString();
//	                if (value == null) {
//	                    return null;
//	                }
//	                return new OrderStatusVo(value);
//	            }
//	        };
//	}
//
//	@Override
//	public JsonSerializer<OrderStatusVo> getJsonSerializer() {
//		 return new JsonSerializer<>() {
//	            @Override
//	            public void serialize(OrderStatusVo value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//	                if (value == null) {
//	                    gen.writeNull();
//	                } else {
//	                    //gen.writeString(value.getValue());
//	                    gen.writeString(value.toString());
//	                }
//	            }
//	        };
//	}
//
//	@Override
//	public Class<OrderStatusVo> getType() {
//		
//		return OrderStatusVo.class;
//	}


