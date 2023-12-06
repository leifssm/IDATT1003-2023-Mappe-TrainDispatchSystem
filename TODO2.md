# Krav til togavgang
- [X] Avgangstid (engelsk: "departure time") - klokkeslettet for når toget går, på formatet tt:mm med 24 timers visning
- [X] Linje (engelsk: "line") - en tekst å formen "L1", "F4" osv. Definerer en strekning som toget kjører på. Flere ulike tog kan kjøre samme strekning da til ulike tidspunkt.
- [X] Tognummer (engelsk: "train number") - en tekst med et unikt nummer innenfor samme dag på formen 602, 45, 1951 etc. Nummeret er unikt innenfor et 24-timers vindu
- [X] Destinasjon (engelsk: "destination")
- [X] Spor (engelsk: "track") - Et heltall som angir hvilket spor toget er satt opp på. Dersom toget ikke har fått tildelt spor ennå, settes spor til -1.
- [X] Forsinkelse (engelsk: "delay") - Angir hvor mye toget er forsinket i timer og minutter. Hvis ingen forsinkelse, settes 00:00.

# Avgrensninger i oppgaven
- [X] Systemet skal støtte kun en stasjon (altså tog som kjører fra én bestemt stasjon).
- [X] Systemet tar ikke hensyn til dato, kun tidspunkt innenfor en dag.
- [X] "Klokken" oppdateres manuelt fra brukermenyen (ingen bruk av systemklokke osv).

- # Funksjonelle krav
- [X] Applikasjonen som skal utvikles, skal ha et tekstbasert brukergrensesnitt i form av en meny.
- [X] Fra denne menyen, skal operatøren på stasjonen kunne:
  - [X] Vise/skrive ut oversikt over togavgangene, sortert etter avreisetidspunkt
  - [X] Legge inn en ny togavgang – det skal ikke være mulig å legge inn et tog med tognummer tilsvarende eksisterende tog i listen.
  - [X] Tildele spor til en togavgang – ved først å søke opp togavgang basert på tognummer, og så sette spor.
  - [X] Legg inn forsinkelse på en togavgang – ved å først søke etter en gitt togavgang basert på tognummer, og deretter legge til forsinkelse.
  - [X] Søke etter en togavgang basert på Tognummer
  - [X] Søke etter togavgang basert på destinasjon
  - [X] Oppdatere klokken (tidspunktet på dagen) – ved å spørre bruker etter nytt klokkeslett.
  - [X] Avslutte applikasjonen
  - [ ] Evt mer
- [X] En togavgang skal automatisk fjernes fra oversikten dersom avreisetidspunktet (pluss eventuell forsinkelse) er tidligere enn klokken (tidspunktet på dagen).
  - Eksempel: har et tog avreisetidspunkt kl 12:35 og er 5 minutter forsinket, skal togavgangen vises i oversikten så lenge klokken (tidspunktet på dagen) er tidligere enn 12:40. I det øyeblikket klokken passerer 12:40, skal togavgangen fjernes fra oversikten.
- [X] Når klokken settes til nytt klokkeslett, skal det ikke være mulig å sette tidspunktet tidligere enn gjeldende tidspunkt. M.a.o. er klokken 12:45, skal det ikke være mulig å sette klokken til 12:40.
# Informasjonstavlen
- [ ] Du velger selv hvordan du vil presentere din informasjonstavle (tilsvarende den i Figur 1), men tavlen skal vise følgende informasjon (i følgende rekkefølge):
  - [ ] Avgangstid på formatet "hh:mm"
  - [ ] Linje
  - [ ] Tognummer
  - [ ] Destinasjon
  - [ ] Eventuell forsinkelse. Hvis ingen forsinkelse skal det ikke vises noe (heller ikke "00:00")
  - [ ] Spor. Hvis ingen spor tildelt, skal ingen ting vises.

# Del 1
- [X] forstå oppgaven/prosjektet
- [X] få opprettet prosjektet som et Maven-prosjekt
- [X] sette prosjektet under versjonskontroll koblet til GitHub/GitLab
- [X] implementere entitetsklassen (klassen som representerer en togavgang)
- [X] implementere testklasse for å teste entitetsklassen.
- [ ] starte på rapporten
- [X] Lage entitetsklasse for tog-avgang (engelsk: "Train Departure")
- [ ] Dokumenter klassen grundig, inkludert
  - [ ] Rolle/ansvar
  - [ ] Hvilke informasjon klassen holder på og hvilken datatyper du har valgt for hver info og hvorfor (begrunnelse)
  - [ ] En vurdering av hvilke informasjon skal kun settes ved opprettelse av instans (m.a.o. ingen set-metoder for disse), og hvilke informasjon må kunne endres etter at instansen er opprettet
  - [ ] Hvordan klassen responderer på ugyldige data - hvilken strategi følger klassen (kaster unntak? setter dummy-verdi?)
- [X] Implementer nødvendige felt i klassen, med egnede datatyper. Tips: for tid kan det være lurt å bruke klassen LocalTime
- [X] Lag enhetstester for å teste at klassen for togavgang fungerer som den skal og er robust. Husk både positive- og negative tester.
- [X] Kjør CheckStyle på koden din med Google-reglene.
- [X] Opprett en klasse som på sikt skal bli brukergrensesnittet og dermed ta seg av all brukerinteraksjon. Opprett to metoder; start() og init().
- [X] Bruk start-metoden til å skrive enkel testkode for å teste at Train-Departure-klassen fungerer iht spek ved f.eks. å opprette 3-4 instanser av klassen, og skrive ut objektene(hint: Kan lønne seg å lage en egen metode for å skrive ut detaljene til en togavgang...).
- [X] Opprett til slutt en klasse som skal være selve applikasjonen og som inneholder public static void main(String[] args)-metoden.
- [X] Opprett main()-metoden. La denne metoden lage en instans av UI-klassen og kall deretter metodene init() og start() til UI-objektet.

# Rapport
- [ ] Sett deg inn i rapportmalen
- [ ] Fyll ut forside, og skriv innledningen
- [ ] Lag et UseCase-diagram som viser hvilken funksjonalitet løsningen skal tilby en bruker.
- [ ] Start på kapitlene om teori og metode.
- [ ] I resultat-kapitelet kan du allerede nå gi en kort beskrivelse av hvilke klasser du ser for deg du vil lage i prosjektet, og vise et klassediagram som viser avhengigheten mellom de.

# Dobbeltsjekk
- [X] Kompilering/bygging og prosjektstruktur:
  - [X] Er prosjektet et Maven-prosjekt med en ryddig og riktig katalogstruktur?
  - [X] Kan prosjektet kompilere uten feil (mvn compile)?
  - [X] Er prosjektet plassert på fornuftig sted på din harddisk (finner du lett tilbake til prosjektet)?
- [X] Versjonskontroll med git:
  - [X] Er prosjektet underlagt versjonskontroll med sentralt repository i GitHub eller GitLab?
  - [X] Finnes det flere innsjekkinger (commits) ?
  - [X] Beskriver commit-meldingene endringene på en kort og konsis måte?
- [ ] Enhetstester:
  - [ ] Har enhetstestene beskrivende navn som dokumenterer hva testene gjør?
  - [ ] Følger de mønstret Arrange-Act-Assert?
  - [ ] Tas det hensyn til både positive og negative tilfeller?
  - [X] Er testdekningen god nok?
  - [X] Kjører samtlige tester uten feil?
  - [ ] Er klassene for togavgang, brukergrensesnitt og applikasjon implementert iht oppgavebeskrivelsen?
- [ ] Kodekvalitet:
  - [ ] Er koden godt dokumentert iht JavaDoc-standard?
  - [X] Er koden robust (validering mm)?
  - [ ] Har variabler, metoder og klasser beskrivende navn?
  - [X] Er klassene gruppert i en logisk pakkestruktur?
- [X] Tognummeret skal ikke forveksles med f.eks. registreringsnummer på bil. M.a.o. det er ikke et nummer som er unikt for akkurat det togsettet (lokomotiv + vogner), men for den spesifikke togavgangen som går på det spesifikke tidspunktet på den spesifikke strekningen

# Mer sjekk
- [X] Satt opp prosjektet riktig, på et egnet sted på din harddisk slik at du har full kontroll på hvor prosjektet befinner seg på din datamaskin?
- [X] Lagt prosjektet ditt til versjonskontroll på GitHub/GitLab ?
- [X] Implementert entitetsklassen for togavgang med alle felt, gode konstruktør(er), aksessor- og eventuelle mutatormetoder? Og med god dokumentasjon av både klasse- og samtlige public metoder iht. JavaDoc standarden? M.a.o. gir CheckStyle feilmeldinger?
- [X] Opprettet enhetstest-klasse for entitetsklassen, der du har laget gode positive- og negative tester? Husk at testdekning (test coverage) på 100% IKKE er et kvalitetsmål for testene dine overhodet. Det er kun en eventuell verifisering for å hjelpe deg til å avdekke hvor stor del av kodebasen din som blir testet av testene dine (hvilket også er viktig), men den sier ingenting om hvor gode testene er (kvalitet).

# Toggruppering
- [X] Velge navn på klassen som skal representere registeret.
- [X] Vurdere hvilken klasse fra Java SDK du tenker er passende å bruke for å lagre alle togavgangene i (ArrayList, HashSet, HashMap osv). Husk å begrunn i rapporten hvorfor du valgte nettopp denne klassen fra SDK'en.

# Minimumkrav til register-klassen
- [X] En metode for å legge til en togavgang i registeret. Dersom det allerede finnes en togavgang med samme tog-nummer som den avgangen som forsøkes lagt til, skal togavgangen ikke legges til registeret og metoden bør gi en eller annen form for tilbakemelding (NB! IKKE til brukeren) til koden som kaller metoden.
- [X] En metode for å søke opp en togavgang basert på det unike tog-nummeret.
- [X] En metode for å søke opp togavgang(er) med en gitt destinasjon.
- [ ] En metode som fjerner togavgang(er) som har avreisetidspunkt tidligere enn et gitt klokkeslett. Husk å også ta med forsinkelse i vurderingen om avgangen skal fjernes.
- [ ] En metode som returnerer alle togavganger som en sortert liste av togavganger sortert stigende på avreisetidspunkt. Her skal du ikke ta hensyn til eventuell forsinkelse. Merk at vi ikke ber om at registeret i seg selv nødvendigvis må være sortert til enhver tid, men at denne metoden skal returnere en sortert samling (eller en iterator til en sortert samling)basert på togavgangene i registeret ditt.
- [ ] NB! Beskriv i rapporten løsningen du valgte å implementere her. Beskriv hvordan du kom frem til løsningen (søkte på nett, ChatGPT, GitHub CoPilot, spurte en venn osv), og forklar med egne ord koden du har implementert (dette for å vise at du forstår koden du har implementert).

# Dersom kode er inspirert av en løsning fra andre (f.eks, nettside) er det viktig at kilde skal henvises og begrunnes.

# Enhetstest av registerklassen
- [ ] Opprett enhets-tester for å teste registerklassen. Husk at det er smart å planlegge på forhånd hva du skal teste i registerklassen og hvordan, og spesielt hvordan du skal teste for å få utført negativ testing. Dette er det lurt å skrive i JavaDoc'en til testklassen FØR du begynner å kode testene.
- [ ] Både positiv(e) og negativ(e) tester. Kontroller gjerne dekningsgrad (test-coverage) for å sjekke at mest mulig av koden i register-klassen dekkes av testene.
- [ ] Oppdatere applikasjonsklassen
  - [ ] init(): Her legger du inn all kode som er nødvendig for å initialisere applikasjonen ved oppstart, som f.eks. å opprette instansen av register-klassen.
  - [ ] start(): Oppdater denne metoden til å ta i bruk den nye register klassen. Opprett 3-4 togavganger som du legger til i registeret. Test deretter noe av funksjonaliteten til registeret. Du kan f.eks. allerede nå tenke på å implementere en metode som skriver ut informasjonstavla til konsollet basert på registrerte togavganger. Det er lurt å lage en egen metode for å skrive ut denne oversikten. Vent med å implementere full meny med input fra brukeren. Dette kommer i del 3 ☺

# Brukergrensesnitt
- [X] Det viktigere at du vektlegger brukervennlighet og et robust grensesnitt, enn et fancy (grafisk) brukergrensesnitt. Anbefales at du holder deg til pensum i emnet
- [X] Med utgangspunkt i opprinnelig kravspesifikasjon, hvilke endringer/forbedringer ville du ha gjort for at applikasjonen skal bli enda mer nyttig og brukervennlig for brukeren? Her har du lov til å avvike noe fra opprinnelig kravspesifikasjon og tilføre dine tanker og ideer. Løsningen din må selvsagt fortsatt oppfylle de grunnleggende funksjonelle brukerkrav, men du står fritt til å endre på designet av klasser, valg av datatyper om du mener forslagene oppgitt i oppgaveteksten ikke er gunstige, og legge til ny funksjonalitet.
- [ ] Med utgangspunkt i designprinsippene coupling og cohesion, hvilke eventuelle endringer vil du gjøre i ditt design? Kan det være aktuelt å introdusere flere klasser for å oppfylle cohesion-prinsippet bedre, eventuelt innføre flere metoder (delegere)?
- [ ] Hvordan møter din løsning prinsippet om lagdelt arkitektur?
- [ ] Beskriv i rapporten hva du har foreslått av endringer og hvordan du har valgt å implementere disse, og hvorfor disse endringene gir et bedre design i trå med designprinsippene vi har lært i emnet.

# Læringsmål som blir testet
- [X] evnen til å levere et selvstendig arbeid
- [ ] evnen til å re-designe (refaktorere) egen løsning uten å tilføre nye feil
- [ ] evnen til å skrive fail-safe/robust kode
- [X] evnen til å skrive kode som følger en etablert kodestil (Google), og som har god lesbarhet og er dokumentert i henhold til anbefalte industristandarder (JavaDoc)
- [ ] evnen til å implementere et robust design etter prinsipper som modularisering, coupling, cohesion, responsibility driven design
- [X] brukervennlighet/god brukerinteraksjon

# Koden skal følge en bestemt kodestil
- [X] Kodestilen skal verifiseres med CheckStyle-plugin (for IntelliJ, VSCode osv) og vise ingen/minimalt med regelbrudd ved levering.
- [X] Klassene samt alle metoder og variabler (felt, parametere, lokale variabler) skal ha gode, beskrivende navn som tydelig gjenspeiler hvilken tjeneste en metode tilbyr, eller hvilken type verdi variablene representerer/holder på.
- [X] Alle navn på klasser, metoder og variabler skal være på engelsk.
- [X] Koden skal være dokumentert (på engelsk) iht standarden for JavaDoc (se hvordan JDK'en er dokumentert, for inspirasjon og som referanse)

# Ekstra informasjon som burde ha kommet på starten av oppgaven
- [ ] Bruke tagger for å markere "release"-versjoner.

# Krav til rapport
- [ ] Rapporten skal være maks 2500 ord!
- [ ] Rapporten fra del 2 videreføres med tilbakemeldingene du fikk fra LA/Faglærer.
Til samtalen for tilbakemelding på del 3:
- [ ] bør rapporten i størst mulig grad ferdigstilles. Kapitlene resultat og drøfting skal nærme seg ferdigstillelse, tilstrekkelig til at det er mulig å gi tilbakemelding.
- [ ] det er viktig at dere dokumenterer opprinnelige funksjoner/klasser contra refaktorerte funksjoner/klasser, og ikke minst hvorfor du valgte å refaktorere.

- [ ] Rapportmalen er for 3 året og er gitt for å gjøre dere vant med den. Den føller denne strukturen
  1. Innledning: Her skriver dere kort om prosjektet som rapporten omhandler.
  2. Bakgrunn – teoretisk grunnlag: All utvikling er basert på noen teorier og
    bakgrunnsinformasjon (f.eks. fra andre publikasjoner eller kilder/bøker). Her skal dere
    kort beskrive de teoriene som dere har benyttet når dere har løst mappen, og henvise til
    kildene der dere har teoriene ifra (bøker, nettressurser osv). IKKE skriv om teorier her
    som der IKKE har benyttet, og ikke omtaler senere i rapporten!
  3. Metode-design: Her beskriver dere kort hvilke verktøy dere har benyttet i prosjektet
    (IDE’er, osv), samt en kort beskrivelse av arbeidsmetoden dere har jobbet etter.
  4. Resultat: Dette er det viktigste og største kapitlet i rapporten. Her skal dere beskrive den
    endelige løsninge dere har kommet frem til. Bruk UML diagrammer som use-case
    diagramm(er), klassediagramm(er), sekvensdiagramm(er) osv. Beskriv kort hver klasse i
    forhold til ansvar/rolle (ikke gå i detalj på alle metoder og felt). Begrunn ditt designvalg
    med referanse til teoriene du har beskrevet i teoridelen.
    Ikke drøft løsningen din i dette kapittelet, det kommer i neste kapittel☺
  5. Drøfting: Her skal du nå se tilbake på din løsning og analysere og drøfte både den og de
    valg du har tatt i forhold til teoriene og metodene du har anvendt (og beskrevet i teori- og
    metode kapittelet.) Tenk som følger: «Det at jeg valgte å designe min løsning i henhold til
    teoriene om.....ved å bruke verktøy og metoder som....har medført at min løsning har endt
    opp å bli enkel å vedlikeholde, lett å utvide.......fordi...»
  6. Konklusjon: En kort oppsummering av prosjektet som helhet. Maks en halv side.

# Generelt i rapporten:
- [ ] Pass på den røde tråden gjennom kapitlene; har du beskrevet en teori i teoridelen, så sørg for å henvis til denne i resultat- og/eller diskusjonsdelen av rapporten. Teorier som ikke henvises til, skal ikke stå i rapporten heller.
- [ ] Terminologier osv: Dersom du ikke har benyttet deg av forkortelser/termer i rapporten så kan du droppe kapittelet ☺

- [ ] Dersom du har benyttet deg av AI-verktøy som støtte/hjelp til å løse mappeoppgaven, skal dette dokumenteres tydelig både i koden (ved kommentarer) og i rapporten.
- [ ] I rapporten skal du beskrive:
  - [ ] Hvilke AI-verktøy du har benyttet (ChatGPT, GitHub CoPilot osv).
  - [ ] Hva du har brukt AI-verktøy til (generere kode, stille spørsmål, hjelp til rapport osv)
  - [ ] Der du har brukt AI-verktøy for hjelp til kode: dokumenter i rapporten de delene av koden som du har fått hjelp til av AI-verktøy, og beskriv hvorfor du mener foreslått kode løser problemet ditt, og hva koden gjør (du må forstå og kunne beskrive hva koden gjør).

# Når du løser oppgaven, bør du dobbeltsjekke følgende:
- [X] Kompilering/bygging og prosjektstruktur:
  - [X] Er prosjektet et Maven-prosjekt med en ryddig og riktig katalogstruktur?
  - [X] Kan prosjektet kompilere uten feil (mvn compile)?
  - [X] Kan samtlige enhets-tester i prosjektet kjøres uten at de feiler?
- [X] Versjonskontroll med git:
  - [X] Er prosjektet underlagt versjonskontroll med sentralt repository i GitHub eller GitLab?
  - [X] Finnes det flere innsjekkinger (commits) ?
  - [X] Beskriver commit-meldingene endringene på en kort og konsis måte?
- [ ] Enhetstester:
  - [ ] Har enhetstestene beskrivende navn som dokumenterer hva testene gjør?
  - [ ] Følger de mønstret Arrange-Act-Assert?
  - [ ] Tas det hensyn til både positive og negative tilfeller?
  - [ ] Er testdekningen god nok?
  - [ ] Kjører samtlige tester uten feil?
- [ ] Er klassene for togavgang, register og applikasjon implementert iht oppgavebeskrivelsen?
- [ ] Kodekvalitet:
  - [X] Er koden godt dokumentert iht JavaDoc-standard, og skrevet i henhold til Google sin kodestil. Husk at også get- og set metoder skal dokumenteres ☺? (Tips: bruk CheckStyle)
  - [X] Er koden robust (validering mm)?
  - [X] Har variabler, metoder og klasser beskrivende navn?
- [X] Er klassene gruppert i en logisk pakkestruktur?