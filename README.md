# merge-maven-plugin
Maven(3) plugin which merges multiple text files into one

## Simple Configuration example
<pre>
&lt;mergers&gt;
&nbsp;&nbsp;&lt;merge&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&lt;target&gt;${build.outputDirectory}/target.txt&lt;/target&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&lt;sources&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;source&gt;src/main/config/${property}/application.txt&lt;/source&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;source&gt;src/main/config/extended/application.txt&lt;/source&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;source&gt;src/main/config/default/application.txt&lt;/source&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&lt;/sources&gt;
&nbsp;&nbsp;&lt;/merge&gt;
&lt;/mergers&gt;
</pre>

## Full pom configuration example
<pre>
&lt;project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd"&gt;
&nbsp;&nbsp;[...]
&nbsp;&nbsp;&lt;dependencies&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&lt;dependency&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;groupId&gt;org.zcore.maven&lt;/groupId&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;artifactId&gt;merge-maven-plugin&lt;/artifactId&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;version&gt;0.0.3&lt;/version&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&lt;/dependency&gt;
&nbsp;&nbsp;&lt;/dependencies&gt;
&nbsp;&nbsp;&lt;build&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&lt;plugins&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;plugin&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;groupId&gt;org.zcore.maven&lt;/groupId&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;artifactId&gt;merge-maven-plugin&lt;/artifactId&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;configuration&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;mergers&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;merger&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;target&gt;${build.outputDirectory}/target.txt&lt;/target&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;sources&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;source&gt;src/main/resources/input0.txt&lt;/source&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;source&gt;src/main/resources/input1.txt&lt;/source&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;source&gt;src/main/resources/inputn.txt&lt;/source&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/sources&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;newLineBetween&gt;false&lt;/newLineBetween&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/merger&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/mergers&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/configuration&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/plugin&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&lt;/plugins&gt;
&nbsp;&nbsp;&lt;/build&gt;
&nbsp;&nbsp;[...]
&lt;/project&gt;
</pre>

## How to run?
From the CLI use <code>mvn org.zcore.maven:merge-maven-plugin:merge</code>, but within the POM it can be easily connected to a phase (most common would be <code>package</code>):

<pre>
&lt;plugin&gt;
&nbsp;…
&nbsp;&lt;executions&gt;
&nbsp;&nbsp;&lt;execution&gt;
&nbsp;&nbsp;&nbsp;&lt;id&gt;merge-files&lt;/id&gt;
&nbsp;&nbsp;&nbsp;&lt;phase&gt;package&lt;/phase&gt;
&nbsp;&nbsp;&nbsp;&lt;goals&gt;
&nbsp;&nbsp;&nbsp;&nbsp;&lt;goal&gt;merge&lt;/goal&gt;
&nbsp;&nbsp;&nbsp;&lt;/goals&gt;
&nbsp;&nbsp;&lt;/execution&gt;
&nbsp;&lt;/executions&gt;
&lt;/plugin&gt;
</pre>
