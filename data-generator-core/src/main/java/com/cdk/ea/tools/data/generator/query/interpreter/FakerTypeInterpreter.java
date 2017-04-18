package com.cdk.ea.tools.data.generator.query.interpreter;

import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.cdk.ea.tools.data.generator.core.DataType;
import com.cdk.ea.tools.data.generator.core.Defaults;
import com.cdk.ea.tools.data.generator.core.FakerProperties;
import com.cdk.ea.tools.data.generator.exception.PropertiesInterpretationException;
import com.cdk.ea.tools.data.generator.query.Query.QueryBuilder;
import com.cdk.ea.tools.data.generator.types.FakerType;
import com.cdk.ea.tools.data.generator.types.FakerType.FakerTypeBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for interpreting details of {@link FakerType} from
 * identifiers.
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 * @since 18-04-2017
 * @version 1.5
 * @see FakerType
 * @see FakerTypeBuilder
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PACKAGE)
class FakerTypeInterpreter extends AbstractTypeInterpreter {

    private static final String[] supportedLocales = { "ca", "ca-CAT", "da-DK", "de", "de-AT", "de-CH", "en", "en-AU",
	    "en-au-ocker", "en-BORK", "en-CA", "en-GB", "en-IND", "en-NEP", "en-NG", "en-NZ", "en-PAK", "en-SG",
	    "en-UG", "en-US", "en-ZA", "es", "es-MX", "fa", "fi-FI", "fr", "he", "in-ID", "it", "ja", "ko", "nb-NO",
	    "nl", "pl", "pt", "pt-BR", "ru", "sk", "sv", "sv-SE", "tr", "uk", "vi", "zh-CN", "zh-TW" };

    /**
     * Interprets and populates the {@link FakerTypeBuilder} and attaches it to
     * {@link QueryBuilder}
     * 
     * @throws PropertiesInterpretationException
     *             if no Faker Key is provided
     */
    @Override
    public void doInterpret(QueryBuilder queryBuilder, String query) {
	FakerTypeBuilder fakerTypeBuilder = new FakerTypeBuilder();
	Set<Object> fakerProps = getPropertyIdentifiers(query);

	String fakerLocale = null;
	String fakerKey = null;

	String locale = getLocale(query);
	Optional<Object> key = fakerProps.stream().filter(prop -> StringUtils.contains(prop.toString(), "."))
		.findFirst();

	if (key.isPresent()) {
	    fakerKey = key.get().toString();
	    log.debug("Setting Faker Key as {}", fakerKey);
	} else
	    throw new PropertiesInterpretationException("No Faker Key Provided for Faker Type");

	if (StringUtils.isNotBlank(locale)) {
	    fakerLocale = locale;
	    if (!ArrayUtils.contains(supportedLocales, fakerLocale)) {
		log.warn("Locale [{}] not supported yet. Defaulting Locale to [{}]", fakerLocale,
			Defaults.DEFAULT_FAKER_LOCALE);
		fakerLocale = Defaults.DEFAULT_FAKER_LOCALE;
	    } else
		log.debug("Setting Faker Locale as {}", fakerLocale);
	} else {
	    log.debug("No Faker Locale specified. Defaulting to locale {}", Defaults.DEFAULT_FAKER_LOCALE);
	    fakerLocale = Defaults.DEFAULT_FAKER_LOCALE;
	}
	FakerProperties fakerProperties = new FakerProperties(fakerKey);

	fakerTypeBuilder.setDataType(DataType.FAKER);
	fakerTypeBuilder.setFakerProperties(fakerProperties);
	fakerTypeBuilder.setLocale(fakerLocale);

	queryBuilder.setTypeBuilder(fakerTypeBuilder);
    }

}
