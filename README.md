# clientTracker
Bet Tracker to be combined with bet server

## Below is a sample of what the client tracker looks like. 
- Below shows what it looks like with sample data populated in
- You can add/edit bets
- If a bet is set to won it will highlight green, lost will highlight red and void it will be yellow. (Is white by default)
- At the top it shows what your profits are for month, total, today and yesterday. Shows how much money you have pending.
- Tags are just a way to categorize the bets. So, for example if you take bets from someone on twitter you can specific taht or if you want to classify something as a promo.
  - This helps with keeping track of profit/loss for certain categories. 
  - Is currently hardcoded to only show the 4 tags specified at the bottom, but in the future I want to add a way to specify for yourself.
- Filters will change what bets are shown in the table. 
- Search text field will search sportsbook, legs and/or tags columns.
![Client Tracker Sample](https://user-images.githubusercontent.com/48994502/223601181-947d6ebf-c9a0-4926-b2fd-6ad410ef849e.png)

## Calculator that lets you calculate if a bet is profitable or not. You put in the fair odds in the able and in the bottom right hand corner you put in what the odds you currently have are. Below all the text boxes it shows how much you should bet and what the win percentage is.
- FK is Full Kelly
- TK is Third Kelly
- QK is Quad Kelly
- EV is Expected Value
- Also calculates what the parlay odds should be if put in multiple odds in the text boxes. 
- Add button saves the bet.
![Screenshot 2023-03-07 at 9 09 58 PM](https://user-images.githubusercontent.com/48994502/223601338-1f9a114d-5e8f-4e23-abff-bd1e6b615520.png)
