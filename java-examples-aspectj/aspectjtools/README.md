# Using AspectJ Tools

Install `aspectj-x.x.x.jar` downloaded via the Downloads page on `https://eclipse.org/aspectj/`.

This example project depends on the ENV variable `ASPECTJ_HOME`. I prefer to extract `aspectj-x-x-x.jar` into `~/.aspectj/aspectj-x.x.x` and add exporting scripts to the `.bash_profile`.

    % mkdir ~/.aspectj/aspectj-1.8.7

    # Run (or double-click) the self-extract .jar to install ~/.aspectj/aspectj-1.8.7
    % java -jar /path/to/downloaded/aspectj-1.8.7.jar

    % vi ~/.bash_profile
    ...
    export ASPECTJ_HOME=${HOME}/.aspectj/aspectj-1.8.7
    export PATH=${ASPECTJ_HOME}/bin:${PATH}

    % source ~/.bash_profile 

    % ls ${ASPECTJ_HOME}/lib
    aspectjrt.jar  aspectjtools.jar  aspectjweaver.jar  org.aspectj.matcher.jar

    % which ajc
    ~/.aspectj/aspectj-1.8.7/bin/ajc

The command-line tool `ajc` is just a script to execute `org.aspectj.tools.ajc.Main`. I recommend you should check out `${ASPECTJ_HOME}/bin/*`. These scripts never auto-detect `ASPECTJ_HOME`, so you need to modify them whenever you move the `aspectj` directory to another path.

