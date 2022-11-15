/*
 * Copyright 2019 All rights reserved.
 */

package cn.muses.config.orika.converter;

import java.util.Base64;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

/**
 * @author jervis
 * @date 2021/6/23.
 */
public class FileStreamConverter extends BidirectionalConverter<byte[], String> {

    @Override
    public String convertTo(byte[] bytes, Type<String> type, MappingContext mappingContext) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    @Override
    public byte[] convertFrom(String s, Type<byte[]> type, MappingContext mappingContext) {
        return Base64.getDecoder().decode(s);
    }
}
