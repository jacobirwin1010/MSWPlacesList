RUNNING INSTRUCTIONS

Open as an android studio project and click debug/run, the program should launch and run
the api key is kept in the res/values/strings.xml, THIS HAS NOT BEEN INCLUDED AS STANDARD AND MUST BE PUT IN

USAGE INSTRUCTIONS	
a press and hold on the map will drop a pin, zoom in and show a group of pins denoting surf lodges within 1km of it.
	After this, the floating action button at the bottom right will display these same results
	as a list showing name, rating and open status (more on that in assumptions).

pressing the refresh floating action button will find all of the surf lodges that are currently on screen, the 
	radius is calculated as the distance from the top left to the bottom right divided by 2.

The user's location will be requested on the first run, this can be accepted or denied. 
If accepted the location button will appear at the top right of the map


ASSUMPTIONS
Mention of assumptions is inline in the code, but they are included here just so they are all in one place:
	-Opening times for each location cannot be returned by the google places API, only the 'open now' status.
	A seperate query must be made for each location. This was implemented successfully then cut out because of the latency.
	A possible solution would be loading the opening times individually when the user selects a location. But this was not included because
	it didnt really seem to apply to the user story. Id like to stress that this assumption would not have happened in a standard work environment,
	questions would definitely be asked.

	-As mentioned in a previous email, the search terms were changed slightly so more relevant locations were returned.
	The type "lodging" and the keyword "surf" were amended slightly to be type "lodging" and keyword "lodge" or "surf"
	
	-It was assumed that the distance of each location on the list did not need to be calculated, as it is fairly clear from the map which is closest.
	Although not ideal, it saves a lot of seperate routing calls to google. This could've been calculated using straight line distance but was 
	ultimately deemed unnecessary.

Id like to reiterate- a lot more questions wouldve been asked in a real world scenario

If you have any questions, dont hesitate to email me