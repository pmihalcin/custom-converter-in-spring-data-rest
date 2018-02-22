# custom-converter-in-spring-data-rest
Minimal sample to simulate https://stackoverflow.com/questions/48931256/how-to-register-converter-in-spring-data-rest-application

* Run Spring boot application
* Navigate to http://localhost:8080/browser/index.html#http://localhost:8080/profile/specifications in browser
* Add custom request headers in HAL browser
  * Accept: application/schema+json
  * Accept-Language: de

You should see JSON schema response with translated enum values:
```
{
  "title": "Specification",
  "properties": {
    "status": {
      "title": "Status",
      "readOnly": false,
      "type": "string",
      "enum": [
        "de_Draft",
        "de_Active",
        "de_Closed",
        "de_Expired"
      ]
    }
  },
  "definitions": {},
  "type": "object",
  "$schema": "http://json-schema.org/draft-04/schema#"
}
```

Imagine that these enum values are used to populated dropdown in frontend UI client in a grid.

When user selects any value, client makes REST call by following e.g. http://localhost:8080/specifications?status=de_Draft what behind the scenes uses `org.springframework.data.querydsl.binding.QuerydslPredicateBuilder` and this component uses `org.springframework.core.convert.support.GenericConversionService.convert`

Without any customization, this REST call fails because:
`Caused by: java.lang.IllegalArgumentException: No enum constant net.homecredit.mer.web.evaluation.spec.Specification.Status.de_Draft`

I essentially need to translate translated value de_Draft back to DRAFT enum value.
This can be accomplished with `EnumTranslator`, so I can add `TranslationStringToSpecificationStatusEnumConverter`:

```
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
```

and register it with:
```
@Configuration
public class RepositoryRestConfig extends RepositoryRestConfigurerAdapter {

    private final TranslationStringToSpecificationStatusEnumConverter converter;

    @Autowired
    public RepositoryRestConfig(TranslationStringToSpecificationStatusEnumConverter converter) {
        this.converter = converter;
    }

    @Override
    public void configureConversionService(ConfigurableConversionService conversionService) {
        conversionService.addConverter(converter);
        super.configureConversionService(conversionService);
    }
}
```

but this fails on startup with circular spring bean dependency reason
```
***************************
APPLICATION FAILED TO START
***************************

Description:

The dependencies of some of the beans in the application context form a cycle:

┌─────┐
|  repositoryRestConfig defined in file [C:\dev\minimal-reproducible-samples\custom-converter-in-spring-data-rest\target\classes\mihalcin\customconverter\RepositoryRestConfig.class]
↑     ↓
|  translationStringToSpecificationStatusEnumConverter defined in file [C:\dev\minimal-reproducible-samples\custom-converter-in-spring-data-rest\target\classes\mihalcin\customconverter\TranslationStringToSpecificationStatusEnumConverter.class]
↑     ↓
|  org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration (field java.util.List org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration.configurers)
└─────┘

```
