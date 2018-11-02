# AndroidHomework

The Airfrov product team loves their pets so below is a hi-fi design of an app that shows a variety of random cats, dogs, and foxes.


App Design :
 I decided to use MVP Architecture in creating the homework so first after creating the project I usually start with creating my usual packages which compose of app, model, ui, & util. Then next is I compile/implement the libraries that I will be using on the project so after looking at sample design provided I decided to use recyclerview, cardview, glide, retrofit, realm, databinding, and MVP. I used cardview and recyclerview for displaying the pets using grid-layout manager since using recyclerview is a more flexible control for handling list data and it can also reuse the cells/cardview.
 I used glide on displaying the images of pets even though it may take a while on loading the images depending on the internet connection it is much more efficient the saving all the images. Next is retrofit by utilizing this library it is much easier to retrieve or upload json via a rest based webservice.
 And with that, I can easily use the provided public API no matter what design or json it will return. Next, I used is realm as a database for the pets object. I choose realm for its speed, ease of use, and it is well documented. Lastly, I used databinding to remove boilerplate, stronger readability and much faster the using findviewbyid. 
 For the MVP under my app is the class application where I initialized the realm and retrofit also this is where 
 API interface for retrofit can be found and all the constants and endpoints. Next is model my model is divided into two packages the data and response since the requirements are just 3 pets and static I only have 3 data and 3 response. Next is the ui, under ui will be all the activity first I created PetActivity and PetTabAdapater that will handle the tabs. Next is I created the Fragment, Presenter, View, and Adapter. All the process is happening on the presenter like saving the pets object, updating the name, and loading the images. Under the adapter are the List of pets and its cardview binding. For the fragment first I will check if the pet object is already existing if it is already existing I will just reload the previous data but if it's not I will load the images first then save it on the object before loading it. I also put a refresh what it will do it just simply refresh all the images and save it. For the Editing of the name I just simply use custom dialog and upon saving I just call the presenter and do the saving of the updated name.
 
 
 
 UI/UX Improvements:
  *Since the requirements are just 3 pets I use static layouts and tabs. But I think in reality it should be dynamic and can accommodate different pets.
  *Also I make the number of pets dynamic but since the requirements are clear to show only 10 pets per tab I limit it to 10.
 
 Backend Improvements:
  *As I mention a while ago the model I created is static for 3 pets but I think it should be dynamic and should only be 1 model and just simply add a column type of pet to accommodate different pets.
  *For the public API since it has different endpoints/url I think it is much more efficient to use rxjava.
  
