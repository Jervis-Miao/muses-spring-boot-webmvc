package cn.muses.config.orika.converter;

import java.util.HashMap;
import java.util.Map;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

/**
 * Map
 *
 * @author shijianpeng
 */
public class MapConverter<K, V> extends BidirectionalConverter<Map<K, V>, Map<K, V>> {

	@Override
	public Map<K, V> convertTo(Map<K, V> kvMap, Type<Map<K, V>> type, MappingContext mappingContext) {
		if (kvMap == null) {
			return null;
		}
		Map<K, V> map = new HashMap<>(kvMap.size());
		kvMap.forEach(map::put);
		return map;
	}

	@Override
	public Map<K, V> convertFrom(Map<K, V> kvMap, Type<Map<K, V>> type, MappingContext mappingContext) {
		if (kvMap == null) {
			return null;
		}
		Map<K, V> map = new HashMap<>(kvMap.size());
		kvMap.forEach(map::put);
		return map;
	}

}
