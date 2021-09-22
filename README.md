# recipe-database
A small project demonstrating some aspects of Spring-Boot

### Purpose
This project can be used as a reference to some basic Spring-Boot functions:
* Rest controller
* JPA
* Validation
* Security

### Running
The easiest way to get the server running is to use 
[Gradle](https://gradle.org/install/).  In the project directory, type:

    gradle bootRun
    
A good client application is [Postman](https://www.postman.com/downloads/).

### API

#### Register a user
You must be registered to access all functions except the H2 console (see
Debugging) and `actuator/shutdown`.

    POST api/register
  
Send a JSON object in the request body with two fields: email and password,
for example:

    {
        "email": "someone@gmail.com",
        "password": "password1"
    }

Emails must contain an @ and a dot (.) and the password must be at least eight
characters long.  If the email is already registered or the user doesn't
validate, the server will respond with the HTTP status 400 Bad Request.  If 
all goes well, the server responds with 200 OK.

After registering, you must use Basic Auth to send your email and password
to access any other functions or the server with return 401 Unauthorized.

#### Create a recipe

    POST api/recipe/new

Send a JSON object with the fields name (String), category (String),
description (String array) and directions (String array).  The following is an
example:

    {
        "name": "Fresh Mint Tea",
        "category": "beverage",
        "description": "Light, aromatic and refreshing beverage, ...",
        "ingredients": ["boiled water", "honey", "fresh mint leaves"],
        "directions": ["Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves", "Mix and let the mint leaves seep for 3-5 minutes", "Add honey and mix again"]
    }

The server will respond with the recipe's ID in a JSON object with the field
ID and an HTTP status of 200 OK.  The email used to authenticate will be
posted as the recipe's creator.  (See Update and Delete.)

#### Get a recipe

    GET api/recipe/{id}

If a valid ID is supplied, the server will respond with a JSON object
containing the recipe's name, category, description, ingredients, and 
directions.  If the ID is not found, the server will respond with 404 Not
Found.

#### Delete a recipe

    DELETE api/recipe/{id}
    
If a valid ID is supplied, the server will delete the recipe with the ID.
Invalid IDs will return an HTTP status of 404. The authenticated user's email
must match the creator's email or the server responds with 403 Forbidden.
A successful delete will have a status of 204 No Content. 

#### Update a recipe

    PUT api/recipe/{id}
    
Send a valid recipe (see Create) as a JSON object in the body of the request 
and a existing ID in the URL.  Invalid IDs will return an HTTP status
of 404. The authenticated user's email must match the creator's email or the 
server responds with 403 Forbidden.  A successful update will have a status
of 204 No Content.

#### Search for recipes

    GET api/recipe/search/?category={category-name}
    GET api/recipe/search/?name={part-of-name}
    
There are two mutually exclusive ways to search for (get a list of) recipes.
If the parameter name is category, the server returns all recipes with 
{category-name}, ignoring case and sorted by descending date as a JSON 
object.  If the parameter name is name, the server returns all recipes 
containing {part-of-name} in the name, case insensitive, in date descending 
order.

### Debuging

To view the database while the server is running, use the H2 console from a 
browser:

    http://localhost:8881/h2-console/
    
To see the SQL statements JPA is using, edit the file 
`resources/application.properties` and set `spring.jpa.show-sql=true`. To get 
a lot of debugging output, set `debug=true`.

### Credit
This is a project from [HyperSkill](https://hyperskill.org/projects/180?track=12).
