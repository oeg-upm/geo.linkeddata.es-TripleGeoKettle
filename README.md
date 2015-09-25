<html>
	<head></head>
	<body>
		<div id="readme" class="clearfix announce instapaper_body md">
			<article class="markdown-body entry-content" itemprop="mainContentOfPage">
				<h2><a name="welcome" class="anchor" href="#welcome"><span class="octicon octicon-link"></span>geo.linkeddata.es-TripleGeoKettle</a></h2>
				<p>Repository where the integration of TripleGeo and GeoKettle is performed, used in the geo.linkeddata.es project.</p>
				<h2><a name="prerequisites" class="anchor" href="#Prerequisites"><span class="octicon octicon-link"></span></a>Prerequisites</h2>
				tripleGEO plugin requires: 
				<ul>
					<li>Java Runtime Environment (JRE) version 1.8 (also called 8.0). You can download a JRE for free <a href="http://www.oracle.com/technetwork/java/index.html">here</a>.</li>
					<li>GeoKettle (GUI version 2.5). You can download it for free <a href="http://www.spatialytics.org/projects/geokettle/">here</a>.</li>
				</ul>
				<h2><a name="installation" class="anchor" href="#Installation"><span class="octicon octicon-link"></span></a>Installation</h2>
				<ol>
					<li>Download the attachment <code>tripleGeoplugin.zip</code> and unzip it in the <code>plugins/steps/</code> directory of Geokettle.</li>
					<li>Download the attachment <code>libTripleGEO.zip</code> and unzip it in the <code>lib/</code> directory of Geokettle.</li>
					<li>Now, you must edit the file <code>*.sh</code>:<br/>
						<ul>
							<li>In the section <code>Libraries used by Kettle</code> add the following lines:<br/>
								<code>for file in $BASEDIR/lib/libTripleGEO/*.jar</code><br/>
								<code>do</code><br/>
									 &nbsp; &nbsp; &nbsp;<code>CLASSPATH=$CLASSPATH:$file</code><br/>
								<code>done</code>
							</li>
							<li>In the section <code>Set java runtime options</code> switch <code>-Xmx512m</code> to <code>-Xmx3072m</code>.</li>
						</ul>
					</li>
					<li>You are now ready to start using the tripleGEO plugin.</li>
				</ol>
				<h2><a name="screenshot" class="anchor" href="#Screenshot"><span class="octicon octicon-link"></span></a>Screenshot</h2>
				<img src="https://github.com/oeg-upm/geo.linkeddata.es-TripleGeoKettle/blob/master/distrib/Screenshot.png">				
				<h2><a name="license" class="anchor" href="#license"><span class="octicon octicon-link"></span></a>License</h2>
				<p>The contents of this project are licensed under the <a href="https://github.com/oeg-upm/geo.linkeddata.es-TripleGeoKettle/blob/master/LICENSE">Apache License</a>.</p>
			</article>
		</div>
	</body>
</html>
