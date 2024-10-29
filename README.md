# Currency-Converter
A simple Android application that allows users to convert currencies.

The requirements are:
1. Users should be able to input an amount and select a currency to convert from and to.
2. The app should fetch live exchange rates from a public API ExchangeRateAPI.
3. Display the converted amount on the screen.
4. Add basic error handling for cases like network failure or invalid inputs.
5. Design a simple and user-friendly.

## 1. Application structure:
Use MVVM architecture because the architecture is flexible, easy to maintain, and effective for Android application development.
- Model: This is the data layer responsible for managing business data, and state. In Android, objects describe data such as Classes, processing functions, etc.
- View: Binds observations and actions by View Model. The view consists of .xml files and Activities/Fragments.
- ViewModel: ViewModel is responsible for updating data to Model and preparing necessary data for View. However, ViewModel is not tied to View.

## 2. Steps to build and run the app:
1. Register API: Register for the ExchangeRateAPI API.
2. Add dependencies:  Add necessary dependencies to the project.
3. Set up MVVM structure for the project.
4. Protect API key: Use secrets-gradle-plugin.
5. Icon svg: Download country flag icons.
6. Making the Layout: Creating the user interface. Handle invalid input using inputType="numberDecimal"
7. ViewBinding Setup: Using ViewBinding to bind views.
8. Dagger-Hilt Setup: Setting up the Hilt to manage dependencies.
9. CurrencyApi Setup: Defining API endpoints.
10. List Resource: Generate a file listing the corresponding currency codes and flag resources.
11. Handling network connection checks.
12. AppModule Setup: Configuring Hilt modules.
13. Repository Setup: Managing data and making network requests.
14. ViewModel Setup: Managing business logic and state.
15. MainActivity Setup: Managing the user interface and handling events.
16. Write unit test: unit test currency conversion and network connectivity

## 3. Link to a video demonstrating the app's key features.
Link video: https://youtu.be/i3D2rzNH4_U



