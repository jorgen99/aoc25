#!/bin/bash

case $1 in
    "")     
        DATE=$(date +"%d")
        ;;
    *)
        DATE=$1
        ;;
esac
YEAR=$(date +"%y")

if [[ "$(uname)" == "Darwin" ]]; then
    SED_OPT='-i ""'
else
    SED_OPT='-i'
fi

[[ ! -d src/jorgen/aoc${YEAR} ]] && mkdir -p {src,test}/jorgen/aoc${YEAR}
NEW_SRC=src/jorgen/aoc${YEAR}/dec${DATE}.clj
NEW_TEST=test/jorgen/aoc${YEAR}/dec${DATE}_test.clj

cp src/jorgen/aoc${YEAR}/dec_template.txt $NEW_SRC
sed $SED_OPT -e "s/__DAY__/$DATE/g" $NEW_SRC

cp test/jorgen/aoc${YEAR}/dec_template_test.txt $NEW_TEST
sed $SED_OPT -e "s/__DAY__/$DATE/g" $NEW_TEST

[[ ! -d resources ]] && mkdir resources
touch resources/dec${DATE}{_sample,_input}.txt
