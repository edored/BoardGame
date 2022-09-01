package com.example.logic;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DBHandler  extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "boardgamesdb";
    private static final String TABLE_NAME = "boardgames";
    // When you do change the structure of the database change the version number from 1 to 2
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_ROWID = "_id";
    private static final String KEY_NAME="name";
    private static final String KEY_GENRE = "genre";
    private static final String KEY_AGE = "age";
    private static final String KEY_MINNUMBEROFPLAYERS = "minNumberOfPlayers";
    private static final String KEY_MAXNUMBEROFPLAYERS = "maxNumberOfPlayers";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                    + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_NAME + " TEXT,"
                    + KEY_AGE + " INTEGER,"
                    + KEY_GENRE + " TEXT,"
                    + KEY_MINNUMBEROFPLAYERS + " INTEGER,"
                    + KEY_MAXNUMBEROFPLAYERS + " INTEGER,"
                    + KEY_DURATION + " INTEGER,"
                    + KEY_DESCRIPTION + " TEXT,"
                    + KEY_IMAGE + " BLOB)";
            db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addNewGame(BasicInformation boardGame) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

//        FileInputStream fis = new FileInputStream("/storage/sdcard/demoImage.jpg");
//        byte[] image= new byte[fis.available()];
//        fis.read(image);


        values.put(KEY_NAME, boardGame.getName());
        values.put(KEY_AGE, boardGame.getAge());
        values.put(KEY_GENRE, boardGame.getGenre());
        values.put(KEY_MINNUMBEROFPLAYERS, boardGame.getMinNumberOfPlayers());
        values.put(KEY_MAXNUMBEROFPLAYERS, boardGame.getMaxNumberOfPlayers());
        values.put(KEY_DURATION, boardGame.getDuration());
        values.put(KEY_DESCRIPTION, boardGame.getDescription());
        values.put(KEY_IMAGE, boardGame.getImage());

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    @SuppressLint("Range")
    public String[] getGameNames() {
        List<String> nameList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        @SuppressLint("Recycle") Cursor res = db.rawQuery( "select * from boardgames", null );
        res.moveToFirst();
        while(!res.isAfterLast()) {
            nameList.add(res.getString(res.getColumnIndex("name")));
            res.moveToNext();
        }

        String[] names = new String[ nameList.size() ];
        nameList.toArray(names);

        return names;
    }

    @SuppressLint("Range")
    public String[] getSearchGameNames(String minNumberOfPlayers, String maxNumberOfPlayers, String age, String duration, String genre) {
        List<String> nameList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        @SuppressLint("Recycle") Cursor res = db.rawQuery( "select * from boardgames WHERE genre = ? OR age >= ? OR duration = ? OR (minNumberOfPlayers >= ? AND maxNumberOfPlayers <= ?)", new String [] {genre, age, duration, minNumberOfPlayers, maxNumberOfPlayers} );
        res.moveToFirst();
        while(!res.isAfterLast()) {
            nameList.add(res.getString(res.getColumnIndex("name")));
            res.moveToNext();
        }

        String[] names = new String[ nameList.size() ];
        nameList.toArray(names);

        return names;
    }

    @SuppressLint("Range")
    public BasicInformation getGame(long position) {
        BasicInformation game = new BasicInformation();
        SQLiteDatabase db = this.getWritableDatabase();

        long id = position + 1;

        @SuppressLint("Recycle") Cursor res = db.rawQuery( "select * from boardgames WHERE _id = ?", new String [] {String.valueOf(id)} );
        if(res != null && res.moveToFirst()) {
            game.setName(res.getString(res.getColumnIndex("name")));
            game.setAge(Integer.parseInt(res.getString(res.getColumnIndex("age"))));
            game.setDuration(Integer.parseInt(res.getString(res.getColumnIndex("duration"))));
            game.setGenre(res.getString(res.getColumnIndex("genre")));
            game.setMinNumberOfPlayers(Integer.parseInt(res.getString(res.getColumnIndex("minNumberOfPlayers"))));
            game.setMaxNumberOfPlayers(Integer.parseInt(res.getString(res.getColumnIndex("maxNumberOfPlayers"))));
            game.setDescription(res.getString(res.getColumnIndex("description")));
            res.close();
        }
        return game;
    }

    @SuppressLint("Range")
    public BasicInformation getGame(String gameName) {
        BasicInformation game = new BasicInformation();
        SQLiteDatabase db = this.getWritableDatabase();

        @SuppressLint("Recycle") Cursor res = db.rawQuery( "select * from boardgames WHERE name = ?", new String [] {String.valueOf(gameName)} );
        res.moveToFirst();
        game.setName(res.getString(res.getColumnIndex("name")));
        game.setAge(Integer.parseInt(res.getString(res.getColumnIndex("age"))));
        game.setDuration(Integer.parseInt(res.getString(res.getColumnIndex("duration"))));
        game.setGenre(res.getString(res.getColumnIndex("genre")));
        game.setMinNumberOfPlayers(Integer.parseInt(res.getString(res.getColumnIndex("minNumberOfPlayers"))));
        game.setMaxNumberOfPlayers(Integer.parseInt(res.getString(res.getColumnIndex("maxNumberOfPlayers"))));
        game.setDescription(res.getString(res.getColumnIndex("description")));

        return game;
    }

    public void clear() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void createMyGames() {

        //addNewGame(new BasicInformation(name, age, minPlayer, maxPlayer, duration, genre, description));
        addNewGame(new BasicInformation("Pandemic", 8, 2, 4, 45, "Strategiespiel", "Im kooperativen Spiel Pandemic seid ihr als hoch qualifizierte Angehörige einer Spezialeinheit für Seuchenbekämpfung tätig. Ihr reist um die ganze Welt und arbeitet zusammen, um die Infektionsgefahren einzudämmen und zugleich die Grundlagen für die Erforschung der Gegenmittel zu legen. Als Arzt, Forscherin oder Logistiker setzt ihr eure jeweiligen Stärken ein, um die Seuchen zu besiegen, bevor diese die Welt in die Knie zwingen können.", null));
        addNewGame(new BasicInformation("Pandemic Hot Zone: Europa", 8, 2, 4, 30, "Strategiespiel", "Drei tödliche Seuchen bedrohen Europa und nur ihr könnt sie aufhalten! Als Mitglieder einer Seuchenspezialeinheit müsst ihr zusammenarbeiten und eure individuellen Fähigkeiten nutzen, um Heilmittel zu entwickeln und zu verhindern, dass sich die tödlichen Seuchen ausbreiten.", null));
        addNewGame(new BasicInformation("Abalone", 8, 2, 2, 20, "Strategiespiel", "Ein Spieleklassiker. Seit mehr als 20 Jahren begeistert dieses abstrakte Spiel die Welt. Nur wer es schafft, seinen Gegner durch logische und vorausschauende Planung der eigenen Spielzüge in die Enge zu treiben, kann dieses Spiel für sich entscheiden.", null));
        addNewGame(new BasicInformation("Azul", 8, 2, 4, 30, "Strategiespiel", "Einstmals von den Mauren ersonnen, vereinnahmten die Portugiesen die ursprünglich blau-weißen Keramikfliesen – genannt Azulejos – ganz für sich. Überwältigt von ihrer prachtvollen Schönheit, welcher König Manuel I. bei einem Besuch der Alhambra in Spanien gewahr wurde, wies er seine Bediensteten an, sie mögen die Wände seines eigenen Palastes in Evora mit ähnlich prachtvollen Fliesen verzieren. Azul lädt euch dazu ein, ihm diesen Wunsch zu erfüllen.", null));
        addNewGame(new BasicInformation("Camel Up", 8, 3, 4, 45, "Strategiespiel", "Es ist an der Zeit, euch als gewiefte Händler zu beweisen und mit eurer Karawane loszuziehen. Aber aufgepasst, manche eurer tüchtigen Kamele sind noch etwas wackelig auf den Beinen … Bietet klug, sorgt für eine optimale Balance und überfordert keines eurer treuen Tiere, denn nur so erreicht ihr euer Ziel, der wohlhabendste und angesehenste Händler zu werden!", null));
        addNewGame(new BasicInformation("Rummikub", 7, 2, 4, 20, "Strategiespiel", "Als in den 40er Jahren in Rumänien das Kartenspiel verboten wurde, hatte der findige Kaufmann Efraim Hertzano eine Idee. Er erfand eine Variante, bei der die Spielkarten durch Zahlenplättchen ersetzt wurden und legte damit den Grundstein für den weltweiten Siegeszug. 1980 war Rummikub das beliebteste Spiel in den USA, wurde in Deutschland zum Spiel des Jahres gekrönt. Der jahrzehntelange Erfolg von Rummikub ist kein Zufall. Das Spiel verfügt über alle Elemente, die zu einem echten Hit gehören: Es ist leicht zu erlernen, geht flott voran und ist so variantenreich, dass jedes Spiel zu einem neuen Erlebnis wird.", null));
        addNewGame(new BasicInformation("Exploding Kittens", 7, 2, 4, 15, "Kartenspiel", "Ein Kartenspiel für Liebhaber von Katzen und Explosionen und Laserstrahlen und … natürlich Ziegen! Exploding Kittens ist das explosionsstärkste Katzenspiel (ähm, Kartenspiel!) aller Zeiten! Eine taktische Version von russischem Roulette – nur eben mit Kätzchen statt Patronen.", null));
        addNewGame(new BasicInformation("Imploding Kittens", 7, 2, 6, 15, "Kartenspiel", "Imploding Kittens ist die erste Spielerweiterung für Exploding Kittens, das preisgekrönten Kartenspiel, welches als erfolgreichste Kickstarter-Kampagne Geschichte geschrieben hat. Diese Erweiterung enthält 20 neue Spielkarten für fünf neuen Aktionsarten und ein „Imploding Kitten“.\n" +
                "\n" +
                "Neu ist, dass die Spielrichtung verändert werden kann. Für Spieler, die die Spielrichtung vergessen, enthält diese Erweiterung eine „Krause der Scham“ – einen lebensgroßen Spielrichtungsanzeiger. Außerdem kann die mögliche Spieleranzahl, durch die neue „Imploding Kitten“-Karte, von fünf auf sechs Spieler erhöht werden. Imploding Kittens ist kein eigenständiges Spiel und nur in Verbindung mit einer anderen Ausgabe von Exploding Kittens spielbar.", null));
        addNewGame(new BasicInformation("Colt Express", 10, 2, 6, 40, "Strategiespiel", "Im späten 19. Jahrhundert war die Eisenbahn des amerikanischen Westens ein beliebtes Ziel für Banditen. Mit waghalsigen Manövern sprangen sie auf die Waggons und in die Abteile hinein, um den ehrbaren Reisenden Geldbörsen und Schmuck zu rauben.", null));
        addNewGame(new BasicInformation("Bears vs Babies", 10, 2, 5, 20, "Kartenspiel", "Bears vs Babies ist ein Kartenspiel, bei dem ihr monströse Bären zusammenstellt (wie die Schachtel schon zeigt), um schreckliche Babys zu fressen – nom nom nom. Es wurde von denselben intelligenten und attraktiven Leuten erfunden, die euch auch schon Exploding Kittens gebracht haben.", null));
        addNewGame(new BasicInformation("Throw Throw Burrito", 7, 2, 6, 15, "Reaktionsspiel", "Throw Throw Burrito ist kein gewöhnliches Kartenspiel, denn ihr werdet eure Freunde und Familien mit knautschigen Burritos bewerfen. Macht also Platz, stellt eure Gläser beiseite und räumt das Essen und Porzellan weg … Es wird chaotisch!", null));
        addNewGame(new BasicInformation("7 Wonders Duel", 10, 2, 2, 20, "Strategiespiel", "In 7 Wonders Duel stehen sich zwei Spieler direkt gegenüber, zwei Zivilisationen auf dem Weg an die Spitze. Ähnlich wie im großen Bruder 7 Wonders entwickelt ihr eure Zivilisation über drei Zeitalter hinweg. Ihr errichtet Gebäude und Weltwunder, verstärkt eure Armeen und macht wissenschaftliche Entdeckungen. Dabei ist 7 Wonders Duel schneller und noch taktischer ausgelegt. Eine gemeinsame Kartenauslage und direkte Konfrontation entscheiden in diesem Spiel für zwei über Sieg und Niederlage.", null));
        addNewGame(new BasicInformation("Dodelido", 8, 2, 6, 20, "Reaktionsspiel", "Bei diesem rasanten Kartenspiel muss schnell geschaltet werden! Wer an der Reihe ist, legt seine oberste Handkarte offen auf einen der drei Ablagestapel. Nun ruft er blitzschnell, was in der Mitte zu sehen ist: Tier, Farbe oder Dodelido! Klingt einfacher, als es ist, zumal die langsame Schildkröte und das bissige Krokodil gerne für Abwechslung sorgen!", null));
        addNewGame(new BasicInformation("Model Motte", 7, 3, 5, 20, "Reaktionsspiel", "Mogeln ist verboten? Nicht bei diesem frechen Kartenspiel! Hier gilt es, durch cleveres Ablegen und geschicktes Schummeln als Erster seine Karten loszuwerden. Eigentlich ganz einfach, gäbe es da nicht die Wächter-Wanze ...", null));
        addNewGame(new BasicInformation("Kniffel", 8, 2, 8, 20, "Würfelspiel", "Seit 50 Jahren erfreut KNIFFEL® die Spielerherzen von Jung bis Alt und ist nach wie vor ein ungeschlagener Klassiker. Und zur guten Spielidee gehört nicht nur die Idee, sondern auch eine schöne Ausstattung. Und die ist genau das, was man bei unserem Classic-Line KNIFFEL® findet. Der Klassiker mit schönen Würfeln, großem Kniffelblock und mit original Kniffelbecher.", null));
        addNewGame(new BasicInformation("Kampf gegen das Spießertum", 18, 2, 10, 0, "Kartenspiel", "Die Spielzeugläden quillen über von Zeitvertreibern, die uns klüger und besser machen wollen. Trotzdem gibt es so viele Vollpfosten auf der Welt. Deshalb müssen wir pädagogisch umdenken und die Erziehungstaktik neu justieren. Legen wir fortan diejenigen Spiele auf den Tisch, die unsere Mutter als nicht salonfähig in den Giftschrank gesperrt hätte. Ob Freunde und Familienmitglieder durch \"Arschlochkind\" und „Kampf gegen das Spießertum“ mehr Grips in die Birne bekommen, wissen wir nicht. Aber der Abend mit ihnen wird erträglicher. Unsere Bewunderung gilt den Entwicklern Kampfhummel: fünf schräge Vögel aus Luzern, deren Ziel es ist mit qualitativ hochwertigen, aktuellen und optisch ansprechenden Spielen den Fans ein fieses Lachen ins Gesicht zu zaubern.", null));
        addNewGame(new BasicInformation("SKYJO", 8, 2, 8, 30, "Kartenspiel", "SKYJO – So heißt das neue spannende Spiel aus dem Hause Magilano, das spaßige Unterhaltung im Freundes- und Familienkreis verspricht. Bei SKYJO geht es darum, über mehrere Spielrunden möglichst wenige Punkte zu sammeln. Denn am Ende jeder Spielrunde werden bei jedem Spieler die Punkte gezählt und zu seinem bisherigen Punktestand hinzuaddiert. Sobald ein Spieler 100 oder mehr Punkte erreicht hat, endet das Spiel und der Spieler mit der niedrigsten Punktezahl wird zum Gewinner. Niedrige Punkte zu sammeln bedeutet, Ausschau nach Karten mit möglichst kleinen und negativen Zahlen zu halten. Durch einige Sonderregeln kommen taktische Elemente ins Spiel, die zu einer überraschenden Drehung der Gewinnaussichten führen können und so für zusätzliche Spannung sorgen.\n" +
                "SKYJO ist eine Bereicherung für lustige und spannende Spielabende und eignet sich ebenso sehr gut als kurzweiliges Spiel für zwischendurch, da es rundenbasiert gespielt wird. Aufgrund seiner kompakten Größe kann das Kartenspiel sehr gut als Begleiter im Urlaub und auf Reisen mitgenommen werden. Was benötigt wird, ist genug Platz zum Auslegen der 12 Karten je Mitspieler. Dafür jedoch bleiben die Hände frei und können beispielsweise zum Knabbern oder zum Punktezählen genutzt werden.", null));
        addNewGame(new BasicInformation("Nova Luna", 8, 1, 4, 30, "Strategiespiel", "„Nova Luna“ ist ein abstraktes Legespiel in astralem Gewand. Der Auftrag: Quadratische Plättchen so anzuordnen, dass die zahlreichen auf ihnen abgebildeten Aufgaben möglichst effektiv erfüllt werden. Ein Plättchen könnte etwa verlangen, dass in seiner Nachbarschaft zwei türkise und zwei rote Teile platziert werden. Damit mehrere der geforderten Aufgaben auf einmal abgehakt werden können, sich sollten die Plättchen bestenfalls gegenseitig erfüllen. Bei den komplexeren Aufgaben hilft die Bildung von Farbgruppen. Basis des Spiels ist die clevere Kaufmechanik: Die Kosten für neue Plättchen zahlen die Spielenden mit Schritten auf einer Mond-Leiste. Den nächsten Zug – und damit die Auswahl aus den folgenden Plättchen – hat immer, wer zuvor am sparsamsten war.", null));

        addNewGame(new BasicInformation("6 nimmt!", 8, 2, 10, 45, "Kartenspiel", "Jeder Spieler erhält 10 Karten, die er möglichst schlau an eine von vier Kartenreihen anlegt. Wer die sechste Karte in eine Reihe legt, muss die ersten fünf Karten nehmen. Und das ist schlecht für die Bilanz, das bringt nämlich Minuspunkte.\n" +
                "\n" +
                "6 nimmt! ist ein raffiniertes Kartenspiel, das einen nicht mehr loslässt. Einfach und schnell gelernt. Mit 2-10 Spielern ist das Spiel auch für große Spielgruppen gut geeignet.", null));
        addNewGame(new BasicInformation("Codenames", 10, 2, 8, 15, "Kommunikationsspiel", "„Das spannende Kommunikationsspiel für clevere Agententeams!\n" +
                "\n" +
                "Jedes Team muss bestimmte Begriffe finden, hinter denen sich die Agenten ihrer Farbe verbergen. Nur die jeweiligen Geheimdienstchefs jedes Teams kennen die richtigen Begriffe und müssen anhand von klugen Hinweisen ihr Team auf die richtige Spur bringen. Dabei dürfen sie aber nur wenig verraten.\n" +
                "\n" +
                "Kommunikation ist alles, nur wer seinem Team die richtigen Hinweise gibt, kann erfolgreich sein!", null));
        addNewGame(new BasicInformation("Der Kartograph", 10, 1, 100, 45, "Strategiespiel", "Die nördlichen Reiche sollen endlich urbar gemacht und dem Königreich Nalos angeschlossen werden. Im Auftrag ihrer Majestät Königin Gimnax sollen die Spieler das Land kartieren. Doch auch die Dragul erheben Anspruch darauf. Die Kartographen müssen kluge Grenzen ziehen, um sich zu behaupten und dabei die begehrtesten Ländereien entdecken.", null));
        addNewGame(new BasicInformation("Disney Villainous - Böse Miene zum guten Spiel", 10, 2, 6, 60, "Strategiespiel", "Jeder Spieler schlüpft in die Rolle seines liebsten Disney-Bösewichts und plant sein individuelles Vorgehen, um am Ende als der größte Bösewicht aller Zeiten hervorzugehen. Seien Sie böse und vor allem schlau: Stellen Sie ihren Mitspielern Fallen, verfluchen Sie Orte, nutzen Sie die Stärke ihrer Verbündeten und werden Sie lästige Helden los!", null));
        addNewGame(new BasicInformation("Disney Villainous - Böse bis ins Mark", 10, 2, 3, 45, "Strategiespiel", "Disney-Fans aufgepasst! Zum Erfolgsspiel Villainous \"Böse Miene zum guten Spiel!\" nun die lang ersehnte 1. Erweiterung mit drei neuen Bösewichten. Ob Böse Königin, Hades oder Dr. Facilier, dem Zwielicht sind keine Grenzen gesetzt. Schlüpft in die Rolle eines Disney Bösewichts und verfolgt euer eigenes, düsteres Ziel, um der größte Schurke aller Zeiten zu sein! Je nach Spieleranzahl, kann das Spiel eigenständig oder in Verbindung mit anderen Bösewichten aus dem Grundspiel gespielt werden.", null));
        addNewGame(new BasicInformation("Das Spiel des Lebens", 8, 2, 45, 60, "Strategiespiel", "Der Klassiker „Das Spiel des Lebens“ lässt den Spielern die Entscheidung offen, welchenWeg sie eingeschlagen. Aber egal wie die Lebensplanung ausfällt, die großen Momentedes Lebens wie Ausbildung, Karriere oder Familie, sind voller Abenteuer und unerwarteter Wendungen. Zum ersten Mal sind auch Haustiere mit dabei, wenn die Spieler sich auf dem Spielplan bewegen,zur Schule gehen, heiraten, eine Familie gründen oder in Urlaub fahren. Ab 8 Jahren geeignet. Für 2 bis 4 Spieler. Aufbau durch Erwachsene erforderlich.", null));
        addNewGame(new BasicInformation("Munchkin 1+2", 12, 3, 6, 90, "Rollenspiel", "Dieses Kartenspiel fängt die Erfahrungen eines Dungeons ein... ohne das nervige Rollenspiel. Alles, was Sie noch zu tun haben, ist, Monster zu töten und magische Gegenstände einzusacken. Und was für magische Gegenstände! Ziehen Sie sich den Geilen Helm an und die Arschtritt-Stifel. Schwingen Sie die grauenvolle Abartige Axt... oder villeicht die Kettensäge der blutigen Zerstückelung.\n" +
                "\n" +
                "Ein schnelles und albernes Spiel, das längst seinen weltweiten Siegeszug angetreen hat. Munchkin kann jede Gruppe in einen hysterischen Lachanfall treiben... und während die anderen lachen, klauen Sie ihr Zeug.\n" +
                "\n" +
                "Das neckische Spielprinzip ist nur der halbe Spaß: Die Spielkarten stecken voller Anspielungen und ironischer Texte und werden von kongenialen Karikaturen abgerundet.", null));
        addNewGame(new BasicInformation("Wizard", 10, 3, 6, 45, "Kartenspiel", "\"Vor langer, langer Zeit mussten die Zauberlehrlinge zur Übung ihrer magischen Fähigkeiten dieses Spiel erlernen, um die Gabe der Vorhersehung zu entwickeln. Gar nicht so einfach, wenn man nicht die Gabe der Prophezeiung besitzt.\"\n" +
                "\n" +
                "Ziel des Spiels ist, die meisten Punkte zu ergattern. Jeder Spieler muss die Anzahl seiner Stiche voraussagen, die er im Laufe des Spiels erhalten wird.\n" +
                "\n" +
                "Die Karten mit Symbolen und stimmungsvollen Zeichnungen versetzen die Spieler zurück in die Zeit von Zauberlehrlingen in Stonehenge. Vier Spielvarianten lassen Wizard auch für wahre Meister der Vorhersehung nie langweilig werden.", null));
        addNewGame(new BasicInformation("Qwixx Deluxe", 8, 2, 4, 90, "Würfelspiel", "Steffen Benndorfs geniales Würfelspiel QWIXX (nominiert zum Spiel des Jahres 2013) ist super einfach und ausgesprochen unterhaltsam. Für alle, die das Würfelvergnügen etwas luxuriöser genießen wollen, gibt es nun QWIXX - DELUXE, mit einer Würfelunterlage aus Filz, vier Filzstiften und vier abwischbaren Tableaus für unbegrenzt viele Partien!\n" +
                "\n" +
                "Die QWIXX - Reihe\n" +
                "Ganz egal ob jung oder alt, ob Enthusiast oder Spielemuffel, in der QWIXX - Familie ist für jeden etwas dabei!\n" +
                "\n" +
                "Sollten die Spielblöcke doch einmal zur Neige gehen, gibt es zu jeder Version selbstverständlich Ersatzblöcke.\n" +
                "\n" +
                "Auch Erweiterungen in Form von Zusatzblöcken sind erhältlich!", null));
        addNewGame(new BasicInformation("Bohnanza", 10, 3, 5, 45, "Kartenspiel", "Bei diesem witzigen Kartenklassiker bauen die Spieler verschiedene Bohnensorten auf ihren Feldern an, um die Ernte möglichst profitabel zu verkaufen.\n" +
                "\n" +
                "Dabei ist eines besonders wichtig: Der Handel mit den Mitspielern. Denn was nutzen die herrlichsten Saubohnen, wenn man nur Brech- oder Feuerbohnen anbauen kann. Also, weg mit dem Zeug.\n" +
                "\n" +
                "Selbst verschenken ist erlaubt - und manchmal sogar der beste Weg zum Sieg.\n" +
                "\n" +
                "Das kultige Spiel um den Bohnenhandel wurde nominiert zum Spiel des Jahres und erhielt die Auzeichnung \"bestes Kartenspiel\" durch Fairplay.", null));
        addNewGame(new BasicInformation("klattschen", 16, 2, 10, 45, "Partyspiel", "Langweilige Party in Sicht? Nicht mit diesem Spiel!\n" +
                "\n" +
                "mit dem Kult-Trinkspiel klattschen der Marke DENKRIESEN hast du das perfekte Spiel um einen feucht-fröhlichen Abend mit deinen Freunden zu erleben. klattschen ist das meistverkaufte Karten-Trinkspiel Deutschlands und besteht aus 60 Spielkarten mit 25 verschiedenen Aufgaben und Anweisungen.\n" +
                "\n" +
                "Wer sich nicht an die Anweisungen hält oder einen Fehler bei der Ausführung macht, muss trinken (im Spiel ‘klattschen’ genannt).\n" +
                "\n" +
                "Außerdem müssen sich alle Spieler während des gesamten Spiels an die 3 Grundregeln halten:\n" +
                "\n" +
                "1. Nicht was Wort ‘trinken’ benutzen (auch nicht: getrunken, trank, trinkst usw.)\n" +
                "\n" +
                "2. Immer mit der linken Hand trinken!.\n" +
                "\n" +
                "3. Nicht fluchen!", null));
        addNewGame(new BasicInformation("Looping Louie", 4, 2, 4, 10, "Kinderspiel", "Auf die Stange, fertig, los!\n" +
                "\n" +
                "In Looping Louie fliegt der Bruchpilot Louie mit seinem batteriebetriebenen Flugzeug über die dreidimensionale Spiellandschaft. Jeder Spieler hat einen eigenen Hühnerstall, auf dessen Dach die Flattertiere in Form von Spielchips auf der Stange sitzen.\n" +
                "\n" +
                "Jetzt heißt es für alle Spieler aufgepasst: Wer nicht reaktionsschnell ist & mit blitzartigen Reflexen den auf den Stall zustürzenden Flieger mit seiner Wippe abwehrt, hat bald nicht mehr alle Hühner auf der Stange.", null));




        //addNewGame(new BasicInformation("", 1, 2, 6, 90, "Strategiespiel", "", null));
        //addNewGame(new BasicInformation("", 1, 2, 6, 90, "Strategiespiel", "", null));
        //addNewGame(new BasicInformation("", 1, 2, 6, 90, "Strategiespiel", "", null));
        //addNewGame(new BasicInformation("", 1, 2, 6, 90, "Strategiespiel", "", null));
        //addNewGame(new BasicInformation("", 1, 2, 6, 90, "Strategiespiel", "", null));
    }
}
