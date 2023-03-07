# filetemplify

I was looking for a tool to create a template from an example project (basically a folder) but I didn't find anything really useful, so I thought about building it myself.

Since my favorite programming language is *Java*, I decided to use it to build this tool.

**filetemplify** copy a list of source folders to the specified corresponding paths and replace in it the keys specified by configuration. Each key is substituted with a string obtained by applying a 'template' to it.

## How to Install and Run the Project

To install this project you need **Apache Maven**. To run it you need **Java 1.8** or greater.    

To run use the `it.pdv.tools.filetemplify.Main` class with an argument to specify a *yaml* file path to use as configuration.

## How to Use the Project

The project behavior is driven by the *yaml* configuration file passed as an argument to the `Main` class.    

Here is an example:
 
	fileNameTemplate: R${key}
	fileContentTemplate: <%=${key}%>
	folderNameTemplate: ${key}
	folders:
	- path: source/folder/path
	  destination: destination/folder/path
	placeholders:
	- key: keyname
	  value: keyvalue
	excludes:
	- excludePattern

In the table below the fields description and details:

| Field Name | Description | Mandatory |    
| --- | --- | --- |    
| `fileNameTemplate` | Define the template string to obtain the destination file names. Use `${key}` to refer to the `placeholder value`. (ex. `<%=${key}%>` --> `<%=keyvalue%>`) | no |    
| `fileContentTemplate` | Define the template string to obtain the destination file contents. | no |    
| `folderNameTemplate` | Define the template string to obtain the destination folder names. | no |    
| `path` | Define the source folder path to templify | yes |    
| `destination` | Define the destination folder path. The source templified folder will be added as a subfolder in this path. | yes |    
| `key` | Define a keyword in the source folder tree to be substituted | yes |    
| `value` | Define an optional replacement for the `key` before to apply the template. | no |    
| `excludes` | Define exclusion patterns to exclude source files\folders. You can use `*` and `?` as wildcards. | no |    

## License

This code is under the **Apache License 2.0**. See the `LICENSE` file for more info.