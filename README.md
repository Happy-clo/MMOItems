Official repository for MMOItems

### Useful Links

- Purchase the plugin here: https://www.spigotmc.org/resources/mmoitems.39267/
- Development builds: https://phoenixdevt.fr/devbuilds
- Official documentation: https://gitlab.com/phoenix-dvpmt/mmoitems/-/wikis/home
- Discord Support: https://phoenixdevt.fr/discord
- Other plugins: https://www.spigotmc.org/resources/authors/indyuce.253965/

### Using MMOItems as dependency

Register the PhoenixDevelopment public repository:

```
<repository>
    <id>phoenix</id>
    <url>https://nexus.phoenixdevt.fr/repository/maven-public/</url>
</repository>
```

And then add both `MythicLib-dist` and `MMOItems-API` as dependencies:

```
<dependency>
    <groupId>io.lumine</groupId>
    <artifactId>MythicLib-dist</artifactId>
    <version>1.6.2-SNAPSHOT</version>
    <scope>provided</scope>
    <optional>true</optional>
</dependency>

<dependency>
    <groupId>net.Indyuce</groupId>
    <artifactId>MMOItems-API</artifactId>
    <version>6.9.5-SNAPSHOT</version>
    <scope>provided</scope>
</dependency>
```
