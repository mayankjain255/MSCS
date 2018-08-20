# MSCS (Metro Smart Card System)

#### Problem Statement:
Implement 'Metro Smart Card System' (MSCS) for Delhi city. For this
application assume there is a single metro line covering 10 stations linearly.
The stations name are A1, A2, A3, A4, A5, A6, A7, A8, A9, A10 as shown below. The travel can be in
any direction.

Travelers have smart cards that behave just like any regular debit card that has an initial balance
when purchased. Travelers swipe-in when they enter a metro station and swipe-out when they exit.
The card balance is automatically updated at swipe-out.

Objective of the exercise is to create an automated system that has following functionalities:
Card should have a minimum balance of Rs 5.5 at swipe-in. At swipe-out, system should calculate the
fare based on below strategies set at the start of the day. The fare must be deducted from the card. Card
should have the sufficient balance otherwise user should NOT be able to exit.

Weekday – Rs. 7 * (Number of stations traveled)
Weekend – Rs. 5.5 * (Number of station traveled if it's Saturday or Sunday)


#### Solution:

User can perform five different operations as follows:
1. Generate new smart card : 
A smart card with unique code is generated, using which user can travel. Every new smart card has Rs. 50 initially.

2. Recharge existing smart card :
User can recharge their smart cards by providing card number and amount.

3. Check balance of existing smart card :
System takes card number from user and display balance amount in smart card.

4. Start journey : 
User can start a journey by providing their card number and station code. The fare calculation starts when user starts the journey.

5. End journey
User can end a journey by providing their card number and station code. Calculated fare amount is deducted from the card balance. In case user ends journey on station where user started then user will not be charged.
