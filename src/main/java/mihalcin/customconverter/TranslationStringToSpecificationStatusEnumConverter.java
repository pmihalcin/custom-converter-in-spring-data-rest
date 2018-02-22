package mihalcin.customconverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.rest.webmvc.json.EnumTranslator;
import org.springframework.stereotype.Component;

@Component
public class TranslationStringToSpecificationStatusEnumConverter implements Converter<String, Specification.Status> {

    private final EnumTranslator enumTranslator;

    @Autowired
    public TranslationStringToSpecificationStatusEnumConverter(EnumTranslator enumTranslator) {
        this.enumTranslator = enumTranslator;
    }

    @Override
    public Specification.Status convert(String source) {
        return enumTranslator.fromText(Specification.Status.class, source);
    }
}