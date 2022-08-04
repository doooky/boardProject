package kdh.boardproject.config

import org.jasypt.encryption.StringEncryptor
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JasyptConfig {
    @Value("\${jasypt.encryptor.password}")
    private val PASSWORD: String? = null
    @Bean("jasyptStringEncryptor")
    fun stringEncryptor(): StringEncryptor {
        val encryptor = PooledPBEStringEncryptor()
        val config = SimpleStringPBEConfig()
        config.password = PASSWORD // 암호화할 키
        config.setPoolSize("1") // 인스턴스 pool
        config.algorithm = "PBEWithMD5AndDES" //암호화 알고리즘
        config.stringOutputType = "base64" // 인코딩 방식
        config.setKeyObtentionIterations("1000") // 반복할 해싱 횟수
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator") // salt 생성 클래스
        encryptor.setConfig(config)
        return encryptor
    }
}