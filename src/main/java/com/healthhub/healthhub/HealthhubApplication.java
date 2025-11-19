package com.healthhub.healthhub;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class HealthhubApplication {


	public static void main(String[] args) {
		loadEnvironmentVariables();
		SpringApplication.run(HealthhubApplication.class, args);
	}

	private static void loadEnvironmentVariables() {
		try {
			// Check if .env file exists
			File envFile = new File(".env");

			if (!envFile.exists()) {
				// Try different locations
				File[] possibleLocations = {
						new File(".env"),
						new File("../.env"),
						new File("healthhub/.env")
				};

				for (File file : possibleLocations) {
					if (file.exists()) {
						envFile = file;
						break;
					}
				}
			}

			Dotenv dotenv = Dotenv.configure()
					.directory(envFile.getParent() != null ? envFile.getParent() : ".")
					.filename(".env")
					.ignoreIfMalformed()
					.ignoreIfMissing()
					.load();

			// Set system properties for each environment variable
			dotenv.entries().forEach(entry -> {
				String key = entry.getKey();
				String value = entry.getValue();
				System.setProperty(key, value);
			});

			System.out.println("Environment variables loaded successfully!");

		} catch (Exception e) {
			System.err.println("Warning: Could not load .env file - " + e.getMessage());
			System.err.println("Trying to load environment variables from system properties...");
			e.printStackTrace();

			// Fallback: try to get from system environment
			String dbUrl = System.getenv("DB_URL");
			if (dbUrl != null) {
				System.setProperty("DB_URL", dbUrl);
			}
		}
	}
}
