# Training Exercise: Ecommerce Prototype
# Overview
A prototype app that lists ecommerce items and their details. The user can mark favourite items to see how data state persists across different screens. This project serves as a foundational exercise for mastering SwiftUI layout and data flow.

# Setup Instructions
Follow these steps to get the development environment ready:

Clone the Repository: git clone https://github.com/rbhagat94/AndroidTraining
Open Android studio
Navigate File -> Open -> Locate clone project
Run the project (ctrl + R), It will automatically run in an emulator

# Project Structure & Architecture
To help you navigate the code, the project is organized into five main layers:

model: Simple Data classes that define our data (e.g., Product).

screen: UI developed via Jetpack Compose

network: Contains the logic for API calls and data fetching from remote servers.

MainActivity.kt: Start of the app

Navigation.kt: For navigating from one screen to other

# Challenge
Your primary objective is to Complete the Detail Page.
The list view is already functional, but clicking a product leads to an unfinished screen. You must implement the design to match the Figma specifications below:
Figma Design: https://www.figma.com/design/mVuNCmgkYkEczj1Sfn1n31/e-commerce-app--Community-?node-id=22-170&t=KuHqVM4FvEZqODzF-0

# Requirements:
Layout Construction: Precisely match the typography, spacing, and image placement using Compose views and make small composable functions.

Api call: Product Listing api has already been integrated to fetch listing data. In a similar fashion fetch product details api with the productId which is already present in the DetailsScreen.kt and cast the response in a Data class. API: https://dummyjson.com/products/1

Integration: Integrating the received response with the UI created.

Clean Code: Use meaningful variable names and break down large views into smaller, reusable subviews (Composition).

