# 1Sec Temporary Mail App ( Made with Compose Multiplatform )

1Sec Temporary Mail is a Kotlin multiplatform app that provides users with temporary email addresses that last for one second. With this app, users can quickly generate disposable email addresses and use them for various purposes such as signing up for websites, receiving verification emails, or protecting their primary email from spam.

# Preview
<img width="1790" alt="Screenshot 2023-07-31 at 3 04 18 PM" src="https://github.com/KapilYadav-dev/1SecMail/assets/69911517/b67618b0-1995-4587-8df6-853af8c7a429">

## Tech Stack

The app is built using the following technologies:

- **Kotlin Multiplatform:** Kotlin Multiplatform allows us to write shared code that can be used across multiple platforms, including Android, iOS, and Desktop.

## Project Structure

The project is organized into the following modules:

- **common:** This module contains shared code that can be used across all platforms. It includes data models, business logic, and network communication using Ktor and Kotlin Serialization.

- **androidApp:** This module contains the Android-specific code, including the user interface (UI) components, Android-specific features, and platform-specific dependencies.

- **iosApp:** This module contains the iOS-specific code, including the UI components, iOS-specific features, and platform-specific dependencies.

- **desktopApp:** This module contains the desktop-specific code, including the user interface (UI) components, desktop-specific features, and platform-specific dependencies.

## Libraries Used

The app utilizes the following libraries:

1. **Ktor:** As the networking library, Ktor provides powerful tools to handle HTTP requests and responses, making it easy to communicate with backend APIs.

2. **Voyager:** The Voyager library simplifies navigation management within the app. It allows for easy routing and screen transitions, making the app flow more efficient.

3. **Kotlin Serialization:** This library helps in parsing JSON data from the backend API and converting it into Kotlin objects. It streamlines data serialization and deserialization.

## How to Build and Run the App

To build and run the app, follow the steps below:

1. Clone the repository to your local machine.

2. Open the project in your preferred Kotlin Multiplatform IDE (e.g., IntelliJ IDEA).

3. Build the project by selecting the appropriate target (Android, iOS, or Desktop) and running the corresponding Gradle task.

4. Run the app on the desired platform's emulator or device.

## Contribution Guidelines

We welcome contributions to improve the app. If you want to contribute, please follow these guidelines:

1. Fork the repository and create a new branch for your feature or bug fix.

2. Make your changes and ensure that the code follows the project's coding standards.

3. Write tests to cover your changes and ensure that existing tests pass.

4. Submit a pull request, explaining the changes and the problem it addresses.

5. A project maintainer will review your pull request, and upon approval, it will be merged into the main branch.

## License

The 1Sec Temporary Mail app is licensed under the MIT License. You can find the details in the [LICENSE](./LICENSE) file.

