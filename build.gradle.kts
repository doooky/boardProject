import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.2"
	id("io.spring.dependency-management") version "1.0.12.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
	id ("org.jetbrains.kotlin.plugin.noarg") version "1.5.21" // 기본 생성자 플러그인 추가
}

noArg {
	annotation("javax.persistence.Entity") // 2

}

group = "kotlinBoard"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	implementation ("org.jetbrains.kotlin:kotlin-reflect:1.2.41")
	implementation ("org.springframework:spring-core:5.3.22")
	implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation ("org.springframework.boot:spring-boot-starter-security")
	implementation ("org.springframework.boot:spring-boot-starter-validation")
	implementation ("org.springframework.boot:spring-boot-starter-web")
	implementation ("mysql:mysql-connector-java:8.0.29")
	implementation ("io.springfox:springfox-boot-starter:3.0.0")
	compileOnly ("org.projectlombok:lombok")
	runtimeOnly ("com.h2database:h2")
	annotationProcessor ("org.projectlombok:lombok")
	testImplementation ("org.springframework.boot:spring-boot-starter-test")
	testImplementation ("org.springframework.security:spring-security-test")

	implementation ("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.4")


	// jwt관련 라이브러리
	implementation ("io.jsonwebtoken:jjwt-api:0.11.2")
	runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.11.2")
	runtimeOnly ("io.jsonwebtoken:jjwt-jackson:0.11.2")

	implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation ("org.javassist:javassist:3.15.0-GA")

	implementation("io.github.microutils:kotlin-logging-jvm:2.0.10")
	implementation("org.slf4j:slf4j-api:1.7.30")
	implementation("com.sun.xml.bind:jaxb-core:2.3.0.1")
	implementation("javax.xml.bind:jaxb-api:2.3.1")
	implementation("com.sun.xml.bind:jaxb-impl:2.3.1")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
