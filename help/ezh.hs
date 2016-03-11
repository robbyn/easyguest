<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE helpset
  PUBLIC "-//Sun Microsystems Inc.//DTD JavaHelp HelpSet Version 1.0//EN"
         "./dtd/helpset_1_0.dtd">

<helpset version="1.0">
  <!-- title -->
  <title>EasyGuest Aide - Guide de l'utilisateur : eZHelp 1.0</title>

  <!-- maps -->
  <maps>
     <homeID>top</homeID>
     <mapref location="ezh.jhm"/>
  </maps>

  <!-- views -->
  <view>
    <name>TOC</name>
    <label>Table Of Contents</label>
    <type>javax.help.TOCView</type>
    <data>ezhTOC.xml</data>
  </view>

  <view>
    <name>Index</name>
    <label>Index</label>
    <type>javax.help.IndexView</type>
    <data>ezhIndex.xml</data>
  </view>

  <view>
    <name>Search</name>
    <label>Search</label>
    <type>javax.help.SearchView</type>
    <data engine="com.sun.java.help.search.DefaultSearchEngine">
      EZHelpSearch
    </data>
  </view>
</helpset>
