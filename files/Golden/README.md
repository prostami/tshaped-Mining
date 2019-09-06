We have extracted a data collection containing the questions and answers posted in StackOverflow from August 2008 up to March 2015. The experiments were performed on three test collections of C#, Android, and Java extracted from the main collection. Java test collection consists of the questions which have tag “java” and the answers to those questions. We also built C# and Android test collections in the same manner. general statistics of the mentioned test collections are as follows:

| Test collection | #Questions | #Answers | #Users |
| --------------- | ---------- | -------- | ------ |
| C#              | 763,717    | 1,453,649| 84,095 |
| Android         | 638,756    | 917,924  | 58,789 |
| Java            | 810,071    | 1,510,812| 83,557 |

In order to generate each golden set, we first explain the skill area’s concept and the ways of generating them for each data collection. Then, in each skill area, we divide users into three groups according to their knowledge level. Finally, we determine users’ shape of expertise based on their knowledge level in the set of their associate skill areas.

## 1. Skill-areas
Each question in Stackoverflow is associated with one or more tags which can be used to explore and search relevant questions. A set of related tags can represent a skill area in terms of programming language. For example, swing, jtable, user interface, jpanel, and jframe, which are semantically related tags, form Java User Interface skill area. In other words, each skill area contains multiple tags. Recruiters usually prefer to find experts on specific skill areas, since skill areas are rather broader topics.
In order to identify skill areas, we went through three steps:
1. Firstly, we extracted top-200 frequent tags from each collection.
2. Secondly, we employed agglomerative clustering (average linkage) algorithm to obtain an initial clustering of tags. To set up
this algorithm, the similarity between each pair of tags (t<sub>1</sub> and t<sub>2</sub>) is calculated using Jaccard Coefficient as follows:
   
   <a href="https://www.codecogs.com/eqnedit.php?latex=Similarity(t_{1},&space;t_{2})&space;=&space;\frac{|Q_{t_{1}}&space;\bigcap&space;Q_{t_{2}}|}{|Q_{t_{1}}&space;\bigcup&space;Q_{t_{2}}|}" target="_blank"><img src="https://latex.codecogs.com/gif.latex?Similarity(t_{1},&space;t_{2})&space;=&space;\frac{|Q_{t_{1}}&space;\bigcap&space;Q_{t_{2}}|}{|Q_{t_{1}}&space;\bigcup&space;Q_{t_{2}}|}" title="Similarity(t_{1}, t_{2}) = \frac{|Q_{t_{1}} \bigcap Q_{t_{2}}|}{|Q_{t_{1}} \bigcup Q_{t_{2}}|}" /></a>
   
   where Q<sub>t</sub> is the set of questions containing tag t.

3. Finally, in order to have a more precise clustering, we asked a group of recruiters to revise the initial clustering according to the most demanding skill areas they are interested in hiring.

The set of skill-areas extracted from C#, Java, and Android test collections are as follows:


**The set of skill-areas extracted from C# test collection (20 skill-areas)**

| Skill-area | Tags                                   |
| ---------- | -------------------------------------- |
| A          | `xamarin`   `android`   `ios`   `mono`   `monotouch` |
| B          | `itextsharp`   `pdf`                        |
| C          | `windows-store-apps`   `windows-runtime`   `winrt-xaml`   `microsoft-metro`   `windows-8` |
| D          | `nunit`   `unit-testing`   `moq` |
| E          | `model-view-controller`   `razor`   `asp.net-mvc`   `asp.net-mvc-2`   `asp.net-mvc-3`   `asp.net-mvc-4`   `asp.net-mvc-5` |
| F          | `json`   `json.net`   `serialization` |
| G          | `nhibernate`   `fluent-nhibernate` |
| H          | `wpf`   `xaml`   `mvvm`   `data-binding`   `binding`   `winforms`   `datagrid`   `listview`   `wpf-controls`   `user-controls`   `datagridview`   `forms`   `winapi`   `user-interface` |
| I          | `entity-framework`   `linq-to-entities`   `entity-framework-4`   `entity-framework-5`   `ef-code-first` |
| J          | `vsto`   `outlook`   `excel`   `ms-word`   `email` |
| K          | `oop`   `design-patterns`   `design`   `inheritance`   `interface`   `generics`   `reflection`   `object`   `class`   `methods` |
| L          | `sockets`   `networking`   `tcp` |
| M          | `regex`   `string` |
| N          | `security`   `encryption`   `active-directory`   `authentication` |
| O          | `soap`   `web-services`   `wcf`   `rest`   `iis`   `sharepoint` |
| P          | `silverlight`   `windows-phone-7`   `windows-phone-8`   `windows-phone-8.1`   `windows-phone` |
| Q          | `debugging`   `visual-studio`   `visual-studio-2010`   `visual-studio-2012`   `visual-studio-2008`   `visual-studio-2013` |
| R          | `unity3d` |
| S          | `task-parallel-library`   `asynchronous`   `async-await`   `multithreading`   `backgroundworker` |
| T          | `xna` |



**The set of skill-areas extracted from Java test collection (26 skill-areas)**

| Skill-area | Tags                                   |
| ---------- | -------------------------------------- |
| A          | `apache-poi`   `excel` |
| B          | `logging`   `log4j` |
| C          | `serialization`   `gson`   `json`   `jackson` |
| D          | `xml-parsing`   `xml`   `jaxb`   `jax-rs` |
| E          | `jersey`   `rest` |
| F          | `soap`   `cxf`   `jax-ws`   `wsdl` |
| G          | `testing`   `junit`   `unit-testing`   `mockito`   `selenium`   `selenium-webdriver` |
| H          | `spring`   `spring-mvc`   `spring-security` |
| I          | `hibernate` |
| J          | `jpa` |
| K          | `struts`   `struts2` |
| L          | `tomcat` |
| N          | `web-applications`   `session` |
| O          | `c`   `c++`   `jni` |
| P          | `layout`   `button`   `awt`   `graphics`   `jtextfield`   `jbutton`   `actionlistener`   `jlabel`   `jframe`   `jpanel`   `user-interface`   `swing`   `jtable` |
| Q          | `constructor`   `object`   `class`   `methods`   `static`   `oop`   `design-patterns`   `design`   `inheritance`   `interface`   `generics` |
| R          | `lucene`   `search` |
| S          | `regex`   `string`   `split` |
| T          | `libgdx`   `opengl` |
| U          | `audio` |
| V          | `google-app-engine`   `gwt` |
| W          | `multithreading`   `concurrency`   `timer` |
| X          | `ssl`   `encryption` |
| Y          | `tcp`   `sockets`   `networking`   `rmi`|
| Z          | `jboss` |
| AA         | `maven`   `maven2` |


**The set of skill-areas extracted from Android test collection (18 skill-areas)**

| Skill-area | Tags                                   |
| ---------- | -------------------------------------- |
| A          | `facebook`   `facebook-graph-api` |
| B          | `xamarin`   `c#`   `mono` |
| C          | `html5`   `jquery`   `jquery-mobile`   `cordova`   `javascript` |
| D          | `maven`   `intellij-idea`   `android-studio`   `gradle`   `android-gradle` |
| E          | `opencv`   `c`   `jni`   `android-ndk`   `c++` |
| F          | `cursor`   `android-sqlite`   `sql`   `sqlite`   `database` |
| G          | `json`   `api`   `web-services`   `rest`   `http`   `http-post` |
| H          | `geolocation`   `gps`   `location`   `adroid-mapview`   `google-maps` |
| I          | `andengine`   `libgdx`   `unity3d`   `opengl-es` |
| J          | `multithreading`   `android-asynctask` |
| K          | `android-gcm`   `push-notifications`   `android-notifications` |
| L          | `android-intent`   `android-activity`   `android-fragmentactivity`   `fragment`   `android-fragments` |
| M          | `security`   `encryption` |
| N          | `android-emulator`   `usb`   `adb`   `debugging`   `emulator`   `avd` |
| O          | `performance`   `testing` |
| P          | `bitmap`   `image`   `gallery`   `imageview`   `android-imageview`   `android-canvas` |
| Q          | `gridview`   `textview`   `android-textview`   `android-linearlayout`   `relativelayout`   `scrollview`   `android-view`   `android-xml`   `android-ui`   `android-layout`   `android-viewpager`   `android-support-library`   `navigation-drawer`   `android-actionbar`   `progress-dialog`   `android-edittext`   `alertdialog`   `android-alertdialog` |
| R          | `achartengine` |


## 2. Knowledge Levels
In the realm of Stackoverflow, the accepted answers associated with a skill area can be considered as an evidence of the author’s knowledge in the corresponding skill area. For a given skill area sa, the knowledge of a user e can be rated into
three different levels including **beginner**, **intermediate**, and **advanced**. In order to define these knowledge levels, we define precision and recall of the answers provided by user e as follows:

– **Precision(sa, e)**: The ratio of the accepted answers associated with user e to all answers provided by him on those which are associated with skill area sa.

– **Recall(sa, e)**: The normalized ratio of the accepted answers provided by user e to the total number of accepted answers associated with skill area sa.

To combine these two measures, we have utilized Harmonic mean as follows:

<a href="https://www.codecogs.com/eqnedit.php?latex=HM&space;=&space;\frac{&space;2&space;*&space;Recall&space;*&space;Precision&space;}{&space;Recall&space;&plus;&space;Precision&space;}" target="_blank"><img src="https://latex.codecogs.com/gif.latex?HM&space;=&space;\frac{&space;2&space;*&space;Recall&space;*&space;Precision&space;}{&space;Recall&space;&plus;&space;Precision&space;}" title="HM = \frac{ 2 * Recall * Precision }{ Recall + Precision }" /></a>

Users of each skill area are sorted in descending order according to their harmonic mean value and then top 5% of the ranking is marked as users who hold advanced knowledge, the next 20% possess intermediate knowledge, and the rest have beginner knowledge on the corresponding skill area. To put it another way, we have considered top 25% intermediated or advanced users and the rest as beginners.


## 3. Categorization of Users
Stackoverflow’s users can be categorized into five groups depending on their count of associated skill-areas and depth of knowledge in such areas as follows:

1. **No-shaped**: The users who do not have advanced and intermediate knowledge in any skill-area.
2. **Hyphen-shaped (Dash-shaped)**: Those users who do not have advanced knowledge in any skill-area, but have intermediate knowledge in one or more skill-areas.
2. **I-shaped**: Those users who have advanced knowledge in one single skill-area, and do not have intermediate knowledge in any skill-area.
3. **T-shaped**: Those users who have advanced knowledge in one single skill-area, as well as intermediate knowledge in one or more skill-areas.
4. **C-shaped (Comb-shaped)**: Those users who have advanced knowledge in more than one skill-areas.

The summary of the shapes of expertise according to the number and level of skill-areas are as follows:

| Shape         | Advanced knowledge | Intermediate knowledge | Beginner knowledge |
| ------------- | ------------------ | ---------------------- | ------------------ |
| No-shaped     | 0                  | 0                      | >=0                |
| Hyphen-shaped | 0                  | >=1                    | >=0                |
| I-shaped      | 1                  | 0                      | >=0                |
| T-shaped      | 1                  | >=1                    | >=0                |
| C-shaped      | >=2                | >=0                    | >=0                |

___

### Note 1:
In the ["T-Shaped Mining: A Novel Approach to Talent Finding for Agile Software Teams"](https://link.springer.com/chapter/10.1007/978-3-319-76941-7_31) paper, for the sake of simplicity, we combined No-shaped and Hyphen-shaped users into one class called 'Non-expert', and we left I-shaped out.

### Note 2:
In the ["T-shaped grouping: Expert finding models to agile software teams retrieval"](https://www.sciencedirect.com/science/article/pii/S0957417418306572) paper, for the sake of simplicity, we combined No-shaped and Hyphen-shaped users into one class called 'Non-expert'.
