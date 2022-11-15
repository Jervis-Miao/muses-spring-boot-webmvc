package cn.muses.config.orika;

import cn.muses.config.orika.converter.FileStreamConverter;
import cn.muses.config.orika.converter.MapConverter;
import cn.muses.web.model.dto.BaseRequestDTO;
import cn.muses.web.model.dto.BaseResponseDTO;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;

/**
 * @author jervis
 * @date 2020/12/1.
 */
public class MusesMapperFactoryConfigurer extends AbstractOrikaMapperFactoryConfigurer {

    @Override
    protected void addConverter(ConverterFactory converterFactory) {
        converterFactory.registerConverter("mapConverter", new MapConverter<>());
        converterFactory.registerConverter("fileStreamConverter", new FileStreamConverter());
    }

    @Override
    protected void addFluidMapper(MapperFactory mapperFactory) {
        mapperFactory.classMap(BaseRequestDTO.class, BaseResponseDTO.class)
            .field("uid", "ret")
            .fieldMap("uidEn", "msg").converter("mapConverter").add()
            .byDefault().register();
    }
}
