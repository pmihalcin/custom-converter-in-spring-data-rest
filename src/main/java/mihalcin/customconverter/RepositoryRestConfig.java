package mihalcin.customconverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.stereotype.Component;

@Component
public class RepositoryRestConfig extends RepositoryRestConfigurerAdapter {

    private TranslationStringToSpecificationStatusEnumConverter converter;

    @Override
    public void configureConversionService(ConfigurableConversionService conversionService) {
        conversionService.addConverter(converter);
        super.configureConversionService(conversionService);
    }

    @Autowired
    public void setConverter(TranslationStringToSpecificationStatusEnumConverter converter) {
        this.converter = converter;
    }
}