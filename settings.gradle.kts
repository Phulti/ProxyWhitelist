/*
    Name of the root-project: In our case it is "multi-module-gradle-project",
    but it can also be something like DataFramework or whatever you are working on
 */
rootProject.name = "ProxyWhitelist"

/*
    If you want to add a new module use:
    include("module-name")

    If you want to rename the subproject to be able to build multiple projects with different names:
    project(":module-2").name = "module-2"
 */
include("proxy")
project(":proxy").name = "proxy"
