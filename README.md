# MyHotels
A simple Android Application for Hotel booking and Recommendations.

# Problem Statement - “Create a Hotel Booking & Recommendation Platform” with the following specifications -
1. Take a sample JSON for a list of Hotels 
        a. The hotel shall have most basic identifying fields
2. Create a few users who will perform the following activities -
        a. Track the visitors on a hotel page
        b. Users make a Draft Booking, where the user tries to book a hotel but don't complete the process
        c. Users book a Hotel i.e. Create a completed booking
        d. Display the activities happening around hotel page (Visits, Draft Bookings, Completed Booking)
 3. Display recommendations of other hotels based on the activities done by the user.
 4. Implement a basic UI with minimal functionality required.
 
 # Technology Stack : 
Android (AndroidX)
XML and Java
Libraries -> GSON , JackSon Faster XML , Adapters , 
XML Components -> Recycler Views , Card Views , Collapsing Toolbars.
 
# Application Flow
1. User Login (Dummy Users Created)
2. Views a List of Hotels (Dummy Hotels with basic Information)
3. Can View Detailed Information of each Hotel.
4. Scrol down to view Recommended Hotels.
5. Book a Hotel.
6. Draft book a hotel (Save for later).
7. View a hotel from the recommendations.

# Recommendations logic
If the user has no bookings then Hotel are recommended on the basis of the "location" of the Hotel user is currently viewing.
Or else based on the "similarity of features" of the previously booked Hotels.

User and Hotel information stored in assets folder as a Users.json and Hotel.json files respectively .
Bookings and Draft Booking details are stored in SharedPreferences as a json string.

