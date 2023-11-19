## Train Departure Class
- [x] Avgangstid - klokkeslettet for når toget går, på formatet tt:mm
med 24 timers visning
- [X] Linje - en tekst å formen "L1", "F4" osv. Definerer en strekning som
toget kjører på. Flere ulike tog kan kjøre samme strekning da til ulike tidspunkt.
- [x] Tognummer - en tekst med et unikt nummer innenfor samme
dag på formen 602, 45, 1951 etc. Nummeret er unikt innenfor et 24-timers vindu
- [x] Destinasjon
- [x] Spor - Et heltall som angir hvilket spor toget er satt opp på. Dersom
toget ikke har fått tildelt spor ennå, settes spor til -1.
- [x] Forsinkelse (engelsk: "delay") - Angir hvor mye toget er forsinket i timer og minutter.
Hvis ingen forsinkelse, settes 00:00.

## Avgrensninger
- [X] Systemet skal støtte kun en stasjon (altså tog som kjører fra én bestemt stasjon).
- [X] Systemet tar ikke hensyn til dato, kun tidspunkt innenfor en dag.
- [x] "Klokken" oppdateres manuelt fra brukermenyen (ingen bruk av systemklokke osv).

## Funksjonelle krav til menyen (ikke begrenset til)
- [x] Vise/skrive ut oversikt over togavgangene, sortert etter avreisetidspunkt (informasjonstavle).
- [X] Legge inn en ny togavgang – det skal ikke være mulig å legge inn et tog med tognummer tilsvarende eksisterende tog i listen.
- [X] Tildele spor til en togavgang – ved først å søke opp togavgang basert på tognummer, og så sette spor.
- [X] Legg inn forsinkelse på en togavgang – ved å først søke etter en gitt togavgang basert på tognummer, og deretter legge til forsinkelse.
- [X] Søke etter en togavgang basert på Tognummer
- [X] Søke etter togavgang basert på destinasjon
- [X] Oppdatere klokken (tidspunktet på dagen) – ved å spørre bruker etter nytt klokkeslett.
- [x] Avslutte applikasjonen
- [X] En togavgang skal automatisk fjernes fra oversikten dersom avreisetidspunktet (pluss eventuell forsinkelse) er tidligere enn klokken (tidspunktet på dagen). Eksempel: har et tog avreisetidspunkt kl 12:35 og er 5 minutter forsinket, skal togavgangen vises i oversikten så lenge klokken (tidspunktet på dagen) er tidligere enn 12:40. I det øyeblikket klokken passerer 12:40, skal togavgangen fjernes fra oversikten.
- [X] Når klokken settes til nytt klokkeslett, skal det ikke være mulig å sette tidspunktet tidligere enn gjeldende tidspunkt. M.a.o. er klokken 12:45, skal det ikke være mulig å sette klokken til 12:40. Informasjonstavlen

## Krav til utseendet til menyen
- [x] Avgangstid på formatet "hh:mm"
- [x] Linje
- [x] Tognummer
- [x] Destinasjon
- [x] Eventuell forsinkelse. Hvis ingen forsinkelse skal det ikke vises noe (heller ikke "00:00")
- [x] Spor. Hvis ingen spor tildelt, skal ingen ting vises.

## Mappe del 1
I første del av mappen skal du fokusere på å:
- [X] forstå oppgaven/prosjektet
- [X] få opprettet prosjektet som et Maven-prosjekt
- [X] sette prosjektet under versjonskontroll koblet til GitHub/GitLab
- [X] implementere entitetsklassen (klassen som representerer en togavgang)
- [X] implementere testklasse for å teste entitetsklassen.
- [X] starte på rapporten
- [X] Lage entitetsklasse for tog-avgang (engelsk: "Train Departure")
  - [ ] Dokumenter klassen grundig, inkludert
  - [ ] Rolle/ansvar
  - [ ] Hvilke informasjon klassen holder på og hvilken datatyper du har valgt for hver info og hvorfor (begrunnelse)
  - [ ] En vurdering av hvilke informasjon skal kun settes ved opprettelse av instans (m.a.o. ingen set-metoder for
        disse), og hvilke informasjon må kunne endres etter at instansen er opprettet
  - [ ] Hvordan klassen responderer på ugyldige data - hvilken strategi følger klassen (kaster
        unntak? setter dummy-verdi?)
  - [X] Implementer nødvendige felt i klassen, med egnede datatyper. Tips: for tid kan det være lurt å bruke klassen LocalTime
  - [ ] Lag enhetstester for å teste at klassen for togavgang fungerer som den skal og er robust. Husk både positive- og negative tester.
- [X] Kjør CheckStyle på koden din med Google-reglene.
- [X] Opprett en klasse som på sikt skal bli brukergrensesnittet og dermed ta seg av all brukerinteraksjon. Opprett to metoder; start() og init().
- [X] Bruk start-metoden til å skrive enkel testkode for å teste at Train-Departure-klassen fungerer iht spek ved f.eks. å opprette 3-4 instanser av klassen, og skrive ut objektene(hint: Kan lønne seg å lage en egen metode for å skrive ut detaljene til en togavgang...).
- [X] Opprett til slutt en klasse som skal være selve applikasjonen og som inneholder main-metoden.
  - [X] Opprett main()-metoden. La denne metoden lage en instans av UI-klassen og kall deretter metodene init() og start() til UI-objektet.

## Rapport
- [ ] Sett deg inn i rapportmalen
- [ ] Fyll ut forside, og skriv innledningen
- [ ] Lag et UseCase-diagram som viser hvilken funksjonalitet løsningen skal tilby en bruker.
- [ ] Start på kapitlene om teori og metode.
- [ ] I resultat-kapitelet kan du allerede nå gi en kort beskrivelse av hvilke klasser du ser for
deg du vil lage i prosjektet, og vise et klassediagram som viser avhengigheten mellom de.

## Viktige sjekkpunkter
Når du løser oppgaven, bør du dobbeltsjekke følgende:
- [ ] Kompilering/bygging og prosjektstruktur:
- [ ] Er prosjektet et Maven-prosjekt med en ryddig og riktig katalogstruktur?
- [ ] Kan prosjektet kompilere uten feil (mvn compile)?
- [X] Er prosjektet plassert på fornuftig sted på din harddisk (finner du lett tilbake til
prosjektet)?
- [X] Versjonskontroll med git:
  - [X] Er prosjektet underlagt versjonskontroll med sentralt repository i GitHub eller
  GitLab?
  - [X] Finnes det flere innsjekkinger (commits) ?
  - [X] Beskriver commit-meldingene endringene på en kort og konsis måte?
- [ ] Enhetstester:
- [ ] Har enhetstestene beskrivende navn som dokumenterer hva testene gjør?
- [ ] Følger de mønstret Arrange-Act-Assert?
- [ ] Tas det hensyn til både positive og negative tilfeller?
- [ ] Er testdekningen god nok?
- [ ] Kjører samtlige tester uten feil?
- [ ] Er klassene for togavgang, brukergrensesnitt og applikasjon implementert iht
oppgavebeskrivelsen?
- [ ] Kodekvalitet:
- [ ] Er koden godt dokumentert iht JavaDoc-standard?
- [ ] Er koden robust (validering mm)?
- [ ] Har variabler, metoder og klasser beskrivende navn?
- [ ] Er klassene gruppert i en logisk pakkestruktur?