plugins {
	id("mod-platform")
	id("dev.kikugie.loom-back-compat")
	alias(libs.plugins.kotlin.serialization)
}

val jvmTargetVersion = if (sc.current.parsed >= "1.20.5") 21 else 17

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(jvmTargetVersion))
	}
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
	compilerOptions {
		jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.fromTarget(jvmTargetVersion.toString()))
	}
}

tasks.withType<JavaCompile>().configureEach {
	options.release.set(jvmTargetVersion)
}

stonecutter {
	val (version, loader) = current.project.split('-', limit = 2)
	properties.tags(version, loader)

	replacements.string(current.parsed >= "1.21.11") {
		replace("ResourceLocation", "Identifier")
		replace("location()", "identifier()")
	}
	replacements.string(current.parsed >= "26.1.2") {
		replace("FabricDataOutput", "FabricPackOutput")
	}
}

platform {
	loader = "fabric"
	dependencies {
		required("minecraft") {
			fabricLikeVersionRange = prop("deps.minecraft")
		}
		required("fabric-api") {
			slug("fabric-api")
			fabricLikeVersionRange = ">=${prop("deps.fabric-api")}"
		}
		required("fabricloader") {
			fabricLikeVersionRange = ">=${prop("deps.fabric-loader")}"
		}
		optional("modmenu") {}
	}
}

loom {
	accessWidenerPath = rootProject.file("src/main/resources/aw/${sc.current.version}.accesswidener")
	runs.named("client") {
		client()
		ideConfigGenerated(true)
		runDir = "run/"
		environment = "client"
		programArgs("--username=Dev")
		configName = "Fabric Client"
	}
	runs.named("server") {
		server()
		ideConfigGenerated(true)
		runDir = "run/"
		environment = "server"
		configName = "Fabric Server"
	}
}

fabricApi {
	configureDataGeneration {
		outputDirectory = file("${rootDir}/versions/datagen/${sc.current.version.split("-")[0]}/src/main/generated")
		client = true
	}
}

repositories {
	mavenCentral()

	strictMaven("https://maven.terraformersmc.com/", "com.terraformersmc") { name = "TerraformersMC" }
	strictMaven("https://api.modrinth.com/maven", "maven.modrinth") { name = "Modrinth" }

	maven { // For ImGuiMC
		url = uri("https://maven.ryanhcode.dev/releases")
		name = "RyanHCode Maven"
	}
	maven { // For Veil
		name = "BlameJared Maven (CrT / Bookshelf)"
		url = uri("https://maven.blamejared.com")
	}
	maven {
		name = "Ladysnake Mods"
		url = uri("https://maven.ladysnake.org/releases")
	}
}

configurations.all {
	resolutionStrategy {
		force("net.fabricmc:fabric-loader:${prop("deps.fabric-loader")}")
	}
}

dependencies {
	minecraft("com.mojang:minecraft:${prop("deps.minecraft")}")
	if (sc.current.parsed < "26") {
		mappings(loom.layered {
			officialMojangMappings()
			if (hasProperty("deps.parchment"))
				parchment("org.parchmentmc.data:parchment-${prop("deps.parchment")}@zip")
		})
	}
	modImplementation("net.fabricmc:fabric-loader:${prop("deps.fabric-loader")}")
	//  implementation(libs.moulberry.mixinconstraints)
	// include(libs.moulberry.mixinconstraints)
	modImplementation("net.fabricmc.fabric-api:fabric-api:${prop("deps.fabric-api")}")
	// modLocalRuntime("com.terraformersmc:modmenu:${prop("deps.modmenu")}")

	modImplementation("org.ladysnake.cardinal-components-api:cardinal-components-base:${prop("deps.cca")}")
	modImplementation("org.ladysnake.cardinal-components-api:cardinal-components-world:${prop("deps.cca")}")
	modImplementation("org.ladysnake.cardinal-components-api:cardinal-components-entity:${prop("deps.cca")}")
	modImplementation("org.ladysnake.cardinal-components-api:cardinal-components-scoreboard:${prop("deps.cca")}")

	include("org.ladysnake.cardinal-components-api:cardinal-components-base:${prop("deps.cca")}")
	include("org.ladysnake.cardinal-components-api:cardinal-components-world:${prop("deps.cca")}")
	include("org.ladysnake.cardinal-components-api:cardinal-components-entity:${prop("deps.cca")}")
	include("org.ladysnake.cardinal-components-api:cardinal-components-scoreboard:${prop("deps.cca")}")

	// Veil
	modImplementation("foundry.veil:veil-fabric-${prop("deps.veil")}") {
		exclude(group = "maven.modrinth")
		exclude(group = "me.fallenbreath")
	}
	// ImGuiMC
	modCompileOnly("foundry.imguimc:imguimc-fabric-${prop("deps.imguimc")}")

	modImplementation(files("libs/m4id-0.1.0-alpha.1-fabric+1.21.1-SNAPSHOT.jar"))

	implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.7.3")
	implementation(libs.serialization.json)
}
