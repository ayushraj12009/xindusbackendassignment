Xindus Project Assignment

Project: Wishlists Mangement

### Description:
   In this project, I have implemented user authentication so that users can Sigin and then log in. Then they can create their own wishlist, view their wishlist, and also delete it.


### How To Run Project:
        - Clone this repository to your local machine.
        - Wait a few minutes for all dependencies to install.
        - Create a database in MySQL, then paste the database name in the application.properties file. You can choose any name.
        - Run the application

### Here are the APIs you can test using Swagger or Postman:
![image](https://github.com/ayushraj12009/xindusbackendassignment/assets/51042913/db959f4c-ae9b-40b8-a2cb-9b431273b679)

- For easy understanding, I've named each API according to its functionality

1. **Signup**
   - Path: `http://localhost:8080/auth/signup`
   - Method: POST
   - Body {
    "fristName": "-----------",
    "lastName": "-----------",
    "userName": -----------,
    "email": "-----------", 
    "password": "-----------" 
}


2. **Signin**
   - Path: `http://localhost:8080/auth/signin`
   - Method: POST
   - Body {            
            "email":"-----------",
            "password":"-----------"
          }

3. **Create a wishlist by user**
   - Path: `http://localhost:8080/api/createWishlistItemByAuthenticateUser`
   - Method: Post
   - Body {            
            "name":"-----------",
            "description":"-----------",
            "price": -----------,
            "email":"-----------", // email should be present in database
            "password":"-----------" //pasword same related to email
          }
   - Description: Paste Signin JWT Token in Header.

4. **Get all wishlist of User**
   - Path: `http://localhost:8080/api/getAllWishlistByUser`
   - Method: GET
   - Body {            
            "email":"-----------",
            "password":"-----------"
          }
   - Description: Paste Signin JWT Token in Header.


5. **Get wishlist By ID**
   - Path: `http://localhost:8080/api/findWishlistAuthenticateUserById/{Paste Id}`
   - Method: GET
   - Body {            
            "email":"-----------",
            "password":"-----------"
          }
   - Description: Paste Signin JWT Token in Header.

6. **Delete wishlist By ID**
   - Path: `http://localhost:8080/api/deleteById/{Paste ID}`
   - Method: Delete
   - Body {            
            "email":"-----------",
            "password":"-----------"
          }
   - Description: Paste Signin JWT Token in Header.


7. **Get wishlist By ID For Any User**
   - Path: `http://localhost:8080/api/findWishlisForAllID/{Paste ID}`
   - Method: GET
   - Body {            
            EMPTY
          }
   - Description: Paste Signin JWT Token in Header.
     
### For Testing Just Run the Testcase File:
8. **There Are Two Testcase File Present In This Project**
   - AuthControllerTest
   - WishlistControllerTest 

