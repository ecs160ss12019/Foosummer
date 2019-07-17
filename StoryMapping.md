FooSummer Story Board - "I'm a Space Pirate"
=================================================================

BHAG
-----------------------------------------------------------------
You are a space pirate that is hiding from the Galaxy Federation Space Police Force (GFSPF) in an asteroid belt. Your goal is to escape the GFSPF while gathering rare materials present in the asteroids.


User Stories
-----------------------------------------------------------------

As a space pirate I want to use a spacecraft and laser to navigate outer space while escaping the space police force and destroying asteroids.

As a space pirate I have a spaceship and I can rotate 360 degrees.

As a game developer, I want to have multiple game levels and features, and have a sense-pleasing game.

As a space police I want to stop and destroy the space pirates using my spaceship lasers.

As s spaceship I want a laser to shoot and mobile functionality to navigate.

As a space pirate I want to use power ups (minerals) available to help me shoot and I have three lives.

As an asteroid belt I want my asteroids to have different sizes and move in different directions, and at different speeds.


Tables
=================================================================
As a space pirate, I want to pilot my spaceship to avoid the GFSPF and collect rare materials by destroying the asteroids.


<hr></hr>
Storyboard
<table width="75%" border="1">
  <tr>
    <td> User Story</td>
    <td> As a space pirate, I want to navigate my spacecraft </td>
    <td> through outer space  </td>
    <td> while gathering rare materials from asteroids </td>
    <td> and being chased by space police. </td>
  </tr>

  <tr>
    <td> Sprint 1 </td>
    <td> I have a spaceship and I can rotate 360 degrees </td>
    <td> Black screen with score and other statistics displayed, and power ups (minerals) are available </td>
    <td> Asteroids float through space with different sizes, directions, and speeds </td>
    <td> Space police show up and approach </td>
  </tr>

  <tr>
    <td> Sprint 2 </td>
    <td> My spaceship has a laser and mobile functionality </td>
    <td> There are multiple levels and space town/port varies with each level </td>
    <td> Asteroids do not collide with each other or police ships or police lasers</td>
    <td> Space police always shoots at the pirate on sight. </td>
  </tr>

  <tr> 
    <td> Sprint 3 </td>
    <td> My spaceship is the Black Pearl </td>
    <td> Visual and sound features are implemented </td>
    <td> Bigger asteroids break into smaller size until </td>
    <td> Space police shoots faster on higher difficulty </td>
  </tr>
</table>

<hr></hr>
Updated storyboard for sprint 1. We removed the plan to change background setting at each new level and divided the statistics display into two sprints.  We also moved varying asteroids size into sprint 2 and refined the asteroids sprint.
<table width="75%" border="1">
  <tr>
    <td> User Story</td>
    <td> As a space pirate, I want to navigate my spacecraft </td>
    <td> through outer space  </td>
    <td> while gathering rare materials from asteroids </td>
    <td> and being chased by space police. </td>
  </tr>

  <tr>
    <td> Sprint 1 </td>
    <td> I have a spaceship and I can rotate 360 degrees </td>
    <td> Black screen with score and other statistics displayed, and power ups (minerals) are available </td>
    <td> Asteroids float through space with different directions and speeds </td>
    <td> Space police show up and approach </td>
  </tr>

  <tr>
    <td> Sprint 2 </td>
    <td> My spaceship has a laser and mobile functionality </td>
    <td> Statistics update accordingly after each event </td>
    <td> Asteroids of varying sizes only collide with player's ship or its laser</td>
    <td> Space police always shoots at the pirate on sight. </td>
  </tr>

  <tr> 
    <td> Sprint 3 </td>
    <td> My spaceship is the Black Pearl </td>
    <td> Visual and sound features are implemented </td>
    <td> Bigger asteroids break into smaller size units</td>
    <td> Space police shoots faster on higher difficulty </td>
  </tr>
</table>



<hr></hr>
Acceptance Tests
<table width="75%" border="1">
  <tr>
    <td> User Story</td>
    <td> As a space pirate, I want to navigate my spacecraft </td>
    <td> through outer space  </td>
    <td> while gathering rare materials from asteroids </td>
    <td> and avoid being caught by space police. </td>
  </tr>

  <tr>
    <td> Sprint 1 </td>
    <td> Player's ship rotates full 360 degrees based on onTouchEvent</td>
    <td> Game statistics always displayed.<br>
         Power ups are floating through space. </td>
    <td> Asteroids float through space in different directions. <br>
         Asteroids vary in sizes. <br>
         Asteroids move at varying speeds</td>
    <td> Space police are spawned.<br>
         Police ships fly in expected paths.<br>
         Police lasers do not interact with powerups.</td>
  </tr>

  <tr>
    <td> Sprint 2 </td>
    <td> Player's ship drives in the correct direction based on onTouchEvent.<br>
         Laser is visible on player's ship.<br>
         Laser is constantly shooting.<br>
         Laser does damage on objects that it hits.<br>
         Player can direct where to shoot.</td>
    <td> Higher level: asteroids move faster.<br>
         Statistics update properly with each new event.<br>
         When hit by player, adds lives OR temporarily increases shooting speed OR creates temporary shield</td>
    <td> Bigger asteroids break into smaller size until.<br>
         Broken asteroids scatter at equal angle from its initial direction.<br>
         Asteroids do not collide with each other or police.<br>
         Points increment properly when asteroids are hit</td>
    <td> Police always shoots at the pirate on sight.<br>
         Police lasers shoot in the direction of the player’s ship (not in random directions).<br>
         Space police do not collide with each other or asteroids.<br>
         If hit points = 0, object is destroyed.</td>
  </tr>

  <tr> 
    <td> Sprint 3 </td>
    <td> Player's spaceship is the Black Pearl</td>
    <td> Sound effects are working.</td>
    <td> Points increment on new events according to new level</td>
    <td> Higher level: More police ships are spawned.<br>
         Higher level: Police lasers shoot faster.</td>
  </tr>
</table>

