package mihalcin.customconverter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.rest.webmvc.json.EnumTranslator;

//@Component
@RequiredArgsConstructor
public class TranslationStringToSpecificationStatusEnumConverter implements Converter<String, Specification.Status> {

    private final EnumTranslator enumTranslator;

    @Override
    public Specification.Status convert(String source) {
        return enumTranslator.fromText(Specification.Status.class, source);
    }
}