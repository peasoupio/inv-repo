repo "https://raw.githubusercontent.com/peasoupio/inv-public-repo/master/io/files/repo.yml"

inv {
    name "template"

    broadcast { PropertiesTemplate } using {
        global { return new PropertiesHelper({ debug(it) }) }
    }
}