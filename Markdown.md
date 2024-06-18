
**Individuell inlämningsuppgift**

**George Youssef - grupparbete tillsammans med Matteus och Siyar.**

2024-06-16

Projektet på [github](https://github.com/georgefs42/securityProject)
 

---
---

**#Beskrivning av det du har bidragit med under grupparbetet.**

Det mesta av arbetet gjordes tillsammans i gruppen och jag hade ansvar för klassen 'SecurityConfiguration'. Resten av projektet har vi arbetat tillsammans med.

---
**#1- Ge exempel på en metod i din grupps webbapplikation. Skriv metoden i ett kodblock.**

Här är ett exempel på en metod i en webbapplikation med Spring Security-konfiguration:

```
// Metod för att konfigurera säkerhetsfilterkedjan
@Bean
public SecurityFilterChain securityChain(HttpSecurity http) throws Exception {
    http
        // Ange behörigheter för HTTP-begäranden baserat på roller
        .authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
                .requestMatchers("/register", "/admin", "/userManager").hasRole("ADMIN") // Tillåt endast ADMIN-rollen att komma åt angivna sökvägar
                .anyRequest().authenticated()) // Alla andra begäranden måste vara autentiserade
        // Konfigurera formulärinloggning
        .formLogin(formLogin ->
            formLogin
                .defaultSuccessUrl("/", true) // Omdirigera till startsidan vid lyckad inloggning
                .failureUrl("/login?error=true") // Omdirigera till inloggningssidan med fel vid misslyckad inloggning
                .permitAll() // Tillåt alla att komma åt inloggningssidan
        )
        // Konfigurera utloggning
        .logout(logout ->
            logout
                .logoutUrl("/performLogout") // URL för att utlösa utloggning
                .logoutSuccessUrl("/login") // Omdirigera till inloggningssidan vid lyckad utloggning
                .permitAll() // Tillåt alla att komma åt utloggnings-URL:en
        )
        // Konfigurera CSRF-skydd med cookies
        .csrf(csrf ->
            csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        );
    return http.build(); // Bygg säkerhetsfilterkedjan
}

````

---

**#2- Förklara följande: vad innebär ordet public/private i metodsignaturen?**

"public": Öppet för alla klasser.

Kan nås från vilken klass som helst, både inom samma paket och från andra paket.
Användning: För metoder som ska vara allmänt tillgängliga.
```
public void myPublicMethod() {
    // Metodens kod
}
```
"private": Endast tillgängligt inom den egna klassen.

Kan endast nås inom den klass där den är definierad.
Användning: För metoder som ska döljas och endast användas internt.

```
private void myPrivateMethod() {
    // Metodens kod
}
```
---
**#3- I metodsignaturen följer därefter ett annat ord ('void' eller namnet på en typ
 eller klass). Vad innebär det om det står 'void'? Vad innebär det om det står en typ eller
 klass?**

"void": Ingen retur.
Det är en metode som returnerar inget värde.
Exempel:
```
public void myMethod() {
    // Ingen return-sats behövs
}
```
"Typ eller klass": Returnerar ett värde av specificerad typ eller klass.
Det ät en Metod som returnerar ett värde av den angivna typen eller klassen.

Exempel med primitiv typ:
```
public int add(int a, int b) {
    return a + b; // Returnerar ett heltal
}
```
Exempel med klass:
```
public String getGreeting() {
    return "Hello, world!"; // Returnerar en sträng
}
```
---
**#4- Efter typen/klassen skriver man metodens namn. Vilken namnkonvention finns för metoder i Java?**

1- Namnkonvention för metoder camelCase (liten bokstav först, stor bokstav för nya ord):

Börjar med liten bokstav.
Varje nytt ord börjar med stor bokstav.
Beskrivande och verb:

2- Använd beskrivande verb för att indikera vad metoden gör.

Exempel:

Enkla namn:
```
public void start() { }
```
Flerordiga namn:
```
public void calculateTotal() { }
public String getCustomerName() { return "George Youssef"; }
```
---
**#5- Efter metodens namn skrivs ibland bara parantes-tecken: (). Vad innebär det om det inte står något mellan paranteserna?**

 Om det inte står något mellan parenteserna efter metodens namn betyder det att metoden inte tar några indataargument.

'()' indikerar att metoden inte tar några indataargument.

Exempel:
```
public void myMethod() {
    // Metodens kod
}
```
---
**#6- Vad kallas det som skickas med till metoden mellan paranteserna? Det finns iallafall två namn för vad det kallas.**


Det som skickas med till metoden mellan parenteserna kallas antingen **parametrar** eller **argument**.

Parametrar: Deklareras i metodens signatur.

Argument: Värden som skickas med till metoden när den anropas.

Exempel:

I följande metod tar a och b parametrar:
```
public int add(int a, int b) {
    return a + b;
}
```
När du anropar metoden add, till exempel så här:
```
int sum = add(5, 3);
```
---
**#7- Här följer ett metod-exempel:**
```
 public void add(int a, int b){
 }
 ```
 **Hur ska du skriva för att returnera summan av a och b i denna metod? Kopiera
 metoden och skriv dit det som saknas. Använd kodblock.**

 För att returnera summan av a och b i metoden add, behöver vi lägga till kod som utför själva additionen och returnerar resultatet.

 ```
 public int add(int a, int b) {
    int sum = a + b;
    return sum;
}
``` 
Denna version av 'add' - medoden retunerar helt enkelt summan av 'a' och 'b' istället för att bara skriva ut den.

---
**#8- Hur ser det ut när man anropar denna metod från en annan metod? Skapa ett
 kodblock och visa hur det kan se ut när denna metod anropas.**

 Exampler för anropa metoden add:
 ```
 public class Main {

    public static void main(String[] args) {
        int result = add(10, 5);
        System.out.println("Resultatet är: " + result);
    }

    public static int add(int a, int b) {
        return a + b;
    }
}
```
I detta exempel:

1- 'add(10, 5)' anropas direkt i 'main'-metoden.

2- Resultatet lagras i variabeln 'result'.

3- Det skrivs ut med 'System.out.println'.

Resultatet kommer vara: 15

---

**#9- Se denna klass:**
 
 ```
 public class HakansClass{
 }
 ```
 **Skapa ett kodblock och kopiera in min klass. Lägg till en tom konstruktor (utan argument) i ditt svar.**

 ```
 public class HakansClass {
    
    // Tom konstruktor (utan argument)
    
    public HakansClass() {
        // Här kan du eventuellt lägga till initialiseringskod om det behövs
    }
    
    // Här kan du lägga till ytterligare metoder eller variabler om det behövs
}
```

---

**#10- Vad är det för skillnad på objekt och primitiva typer i Java? Svara med ett kodblock där du visar 1) skapandet av en primitiv typ med ett värde, 2) skapandet av ett objekt (en instans av en klass).**

Primitiv typ: Representerar enkla värden, inte objekt, ingen metod eller egenskap.

Exempel på skapande av en primitiv typ (int):

```
int number = 10;  // Skapar en primitiv typ 'int' med värdet 10
System.out.println("Värde av number: " + number);
```
Objekt: Instans av en klass, kan ha attribut och metoder, används för att modellera mer komplexa datastrukturer och beteenden.

Exempel på skapande av ett objekt:

```
public class Person {
    private String name;
    
    public Person(String name) {
        this.name = name;
    }
    
    public void greet() {
        System.out.println("Hej, mitt namn är " + name);
    }
}

// Skapar ett objekt av klassen Person
Person personObject = new Person("Alice");
personObject.greet();  // Anropar metoden greet på objektet
```
---

**#11- Med Spring används speciella @Bean-annotationer. Det är metoder som annoteras på det sättet. Konventionen i Spring är att metoder annoterade med @Bean namnges med den typ som returneras, ex:**

 ```
 @Bean
 public HakansClass hakansClass(){
 return new HakansClass();
 }
````

**Men hur är annars namnkonventionen i Java när det gäller metoder? Vad brukar man börja metodnamnet med (tips: en ordklass)?**

I Java följer metoder vanligtvis **camelCase**-namnkonventionen, där man börjar med en liten bokstav och använder stor bokstav för varje nytt ord. Det är vanligt att börja metodnamnet med ett verb eller en verbfras, eftersom metoder representerar handlingar eller operationer.

---
**#12- Vad innebär annotationen @Override och när används den?**

'@Override' används för att markera att en metod i en subklass överskuggar en metod i dess superklass.
Det ger både dokumentation för kodens avsikt och kompilatoriskt stöd för att identifiera felaktigheter i metodöverlagring.

---
**#13- När en klass ärver av en annan klass använder man keywordet extends, ex**
 
```
public class MyCustomAuthenticationProver extends DaoAuthenticationProvider{
 public MyCustomAuthenticationProver(){
 }
 }
 ````
 **Klassen DaoAuthenticationProvider (som finns i Spring) har denna metod:**

 ```
 authenticate(Authentication auth)
 ```` 
**kan jag kalla på den metoden i subklassen i MyCustomAuthenticationProvider?
Hur skriver jag för att kalla på den metoden, som alltså är deklarerad i superklassen?**

Ja, du kan använda metoden authenticate(Authentication auth) från superklassen DaoAuthenticationProvider i din subklass MyCustomAuthenticationProver direkt genom att skriva metodens namn.

Exempel:

```
public class MyCustomAuthenticationProver extends DaoAuthenticationProvider {

    public MyCustomAuthenticationProver() {
        // Konstruktor för subklassen
    }

    public void someMethod() {
        Authentication auth = ...; // Skapa ett Authentication-objekt här
        authenticate(auth); // Anropa metoden från superklassen
    }

}
````
Genom att bara skriva 'authenticate(auth)' i subklassens metod används metoden 'authenticate' som är ärvt från superklassen 'DaoAuthenticationProvider'.

---
**#14 - Vilka uppgifter har PasswordEncoder-@Bean? Dels vid registrering, dels vid inloggning?**

Vid registrering: PasswordEncoder-beanet krypterar eller hashar lösenordet innan det sparas i databasen.

Exempel:
```
String encodedPassword = passwordEncoder.encode(rawPassword);
// Spara encodedPassword i databasen tillsammans med användarens övriga information
````

Vid inloggning: PasswordEncoder-beanet används för att jämföra det angivna lösenordet med det sparade krypterade/hashade lösenordet för autentisering.

Exempel:
```
boolean matches = passwordEncoder.matches(rawPassword, encodedPasswordFromDatabase);
// Returnerar true om lösenordet matchar, annars false

````

På detta sätt hjälper PasswordEncoder-beanet till att säkerställa att lösenord hanteras på ett säkert sätt både vid registrering och inloggning i en Spring Security-applikation.

---
**15- Logback är default-implementationen för loggning i Spring. Skapa koden till
 en konfigurationsfil för logback.xml. Filen ska innehålla: en appender som loggar till fil,
 en ROLLING file-appender (som genererar en loggfil per dag), en logger för
 baspaketet se.sprinto.hakan.securityapp och som loggar till ROLLING file, samt en
 root-logger som loggar till den andra filen. Använd kodblock, men detta är xml så du
 skriver ```xml istället för java.**

 Här är en exempelkonfiguration för logback.xml som uppfyller kraven du nämnde:

 ´´´
 <?xml version="1.0" encoding="UTF-8"?>
<configuration>

    <!-- Appender som loggar till en vanlig fil -->
    <appender name="FILE" class="ch.qos.logback.core.FileAppender">
        <file>logs/application.log</file>
        <encoder>
            <pattern>%date [%thread] %-5level %logger{35} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- ROLLING file-appender som genererar en loggfil per dag -->
    <appender name="ROLLING" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>logs/application</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>logs/application-%d{yyyy-MM-dd}.log</fileNamePattern>
        </rollingPolicy>
        <encoder>
            <pattern>%date [%thread] %-5level %logger{35} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- Logger för paketet se.sprinto.hakan.securityapp som använder ROLLING appender -->
    <logger name="se.sprinto.hakan.securityapp" level="INFO" additivity="false">
        <appender-ref ref="ROLLING"/>
    </logger>

    <!-- Root logger som använder FILE appender -->
    <root level="INFO">
        <appender-ref ref="FILE"/>
    </root>

</configuration>


---
**#16- Vad innebär HTTP-koderna: 200, 401, 403 och 404?**

Här är en kortfattad förklaring av HTTP-statuskoderna:

1. **200 OK**: Begäran har utförts framgångsrikt och resursen har levererats.
2. **401 Unauthorized**: Åtkomst kräver autentisering, men autentiseringen har misslyckats eller saknas.
3. **403 Forbidden**: Servern förstår begäran men vägrar att tillåta åtkomst till resursen.
4. **404 Not Found**: Den begärda resursen kunde inte hittas på servern.

Dessa statuskoder används för att ge tydlig återkoppling om resultatet av en HTTP-begäran mellan klient och server.

---
---
