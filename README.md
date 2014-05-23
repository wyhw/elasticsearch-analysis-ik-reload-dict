elasticsearch-analysis-ik-reload-dict
=====================================

A elasticsearch plugin, Can reload dictionary for elasticsearch-analysis-ik

##Requirement
elasticsearch-analysis-ik source for this fix:

[Reload IK dictionary fix ](https://github.com/wyhw/elasticsearch-analysis-ik/commit/f9b7cfc2f09d37f34e86db2305af8dc309c18789)


get jar package:

	wget -O elasticsearch-analysis-ik-1.2.6.jar http://github.com/wyhw/elasticsearch-analysis-ik/blob/master/elasticsearch-analysis-ik-1.2.6.jar?raw=true --no-check-certificate

##Instalation
Run 

	cd elasticsearch

	wget http://github.com/wyhw/elasticsearch-analysis-ik-reload-dict/blob/master/elasticsearch-analysis-ik-reload-dict-0.0.2.zip?raw=true --no-check-certificate

	bin/plugin --url file://`pwd`/elasticsearch-analysis-ik-reload-dict-0.0.2.zip --install elasticsearch-analysis-ik-reload-dict

then Restart elasticsearch.

##Usage

####View: 

	http://localhost:9200/_reloadikdict

####Reload IK dictionary

	http://localhost:9200/_reloadikdict?reload=1


##Version mapping

	master   =>    Elasticsearch 1.2.x
	
	0.0.2    =>    Elasticsearch 1.2.x
	
	0.0.1    =>    Elasticsearch 1.0.x
