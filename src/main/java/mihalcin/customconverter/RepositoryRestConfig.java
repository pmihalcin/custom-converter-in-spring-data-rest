package mihalcin.customconverter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

//@Configuration
@RequiredArgsConstructor
public class RepositoryRestConfig extends RepositoryRestConfigurerAdapter {

    private final TranslationStringToSpecificationStatusEnumConverter converter;

    @Override
    public void configureConversionService(ConfigurableConversionService conversionService) {
        conversionService.addConverter(converter);
        super.configureConversionService(conversionService);
    }
}