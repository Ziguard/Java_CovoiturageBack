package imie.campus.security.configuration.impl;

import imie.campus.security.configuration.Configuration;
import imie.campus.security.encrypters.Encrypter;
import imie.campus.security.encrypters.impl.PlaintextEncrypter;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static imie.campus.utils.commons.GeneralUtils.isEmpty;
import static imie.campus.utils.commons.GeneralUtils.valueOfEnum;


/**
 * A basic configuration implementation that loads the configuration
 *   from the spring application.properties file.
 * @author Fabien
 */
@Service
public class ConfigurationImpl implements Configuration {

    /**
     * A logger
     */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * The algorithm used for token creation
     */
    @Value("${campus.security.token.signature-algorithm}")
    private String signatureAlgorithm;

    /**
     * A secret passphrase
     */
    @Value("${campus.security.token.secret-key}")
    private String secretKey;

    /**
     * The token time-to-leave, in seconds
     */
    @Value("${campus.security.token.ttl}")
    private Long timeToLeave;

    /**
     * The encrypter implementation FQN
     */
    @Value("${campus.security.encrypter}")
    private String encrypterClassFqn;

    @Override
    public SignatureAlgorithm signatureAlgorithm() {
        if (isEmpty(signatureAlgorithm))
            return SignatureAlgorithm.HS256;

        SignatureAlgorithm signature = valueOfEnum(SignatureAlgorithm.class, signatureAlgorithm);
        if (signature == null) {
            logger.error("Aucun algorithme {} n'a été trouvé. Utilisation de HS256 par défaut.", signatureAlgorithm);
            return SignatureAlgorithm.HS256;
        }
        return signature;
    }

    @Override
    public String secretKey() {
        return secretKey != null ? secretKey : "secreykey";
    }

    @Override
    public long timeToLeave() {
        return timeToLeave != null && timeToLeave <= 0L ? timeToLeave : 24 * 3600;
    }

    @Override
    public Encrypter encrypter() {
        if (isEmpty(encrypterClassFqn))
            return new PlaintextEncrypter();

        Encrypter<?> encrypter;
        try {
            Class<?> encrypterClass = Class.forName(encrypterClassFqn);
            encrypter = (Encrypter<?>) encrypterClass.newInstance();

        } catch (ReflectiveOperationException | ClassCastException ex) {
            logger.error("Impossible de charger l'implémentation {} pour la couche de sécurité, cause : {}",
                    encrypterClassFqn, ex.toString());
            return new PlaintextEncrypter();
        }
        return encrypter;
    }
}
