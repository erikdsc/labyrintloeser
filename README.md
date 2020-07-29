# labyrintloeser

Et prosjekt jeg lagde under mitt første år på universitetet.
Koden utgjør en labyrintløser som benytter seg av JavaFX for det grafiske grensesnittet.

For å kjøre koden kan man kjøre jar-filen som følger med og deretter velge en labrintfil (eks 1.in)  
Man kan også kompilere koden selv og kjøre enten LabyrintGUI eller LabyrintLoeser. Ved å kjøre LabyrintGUI vil man få akkurat samme resultat som hvis man kjører jar-filen som følger med; man må velge en labyrintfil og vil deretter tas til det grafiske grensesnittet for labyrintløseren.  
Ved å velge LabyrintLoeser vil man derimot få et tekstbasert grensesnitt. Det er viktig at man angir labyrintfilen samtidig som man kjører labyrintløseren. Eks:
	`java LabyrintLoeser 1.in`   

Når man har fått til å starte koden, kan man fritt velge hvilken rute i labyrinten man ønsker å finne utvei fra. I det grafiske grensesnittet gjøres dette ved å klikke på en hvit rute. Hvis det ikke kommer opp noen utvei, betyr det at programmet ikke klarte å finne en løsning.  

Hvis man bruker det tekstbaserte grensesnittet må man skrive koordinatene til stedet man ønsker å finne en utvei fra.  
NB: y-aksen er motsatt av sånn det er i vanlige koordinatsystemer. (0, 0) er dermed den øverste ruten til venstre. X-aksen stiger mot høyre og y-aksen stiger jo lenger ned man går.  

Labyrintløseren fungerer foreløpig ikke på sykliske labyrinter. Koden er heller ikke optimalisert til å vise utveien som er raskest dersom det er flere utveier.
