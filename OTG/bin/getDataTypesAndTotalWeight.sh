

HOME="C:/MyWork"
PROJECT_HOME=${HOME}/OTG
JQ=${PROJECT_HOME}/bin/jq
INPUT_FILE=${PROJECT_HOME}/data/otg-datasample_pretty.json

echo "Data Types"

for e in `cat ${INPUT_FILE} | ${JQ} '[.[] | {header: .header.type} ]' | grep ":"  | cut -d':' -f2 | sort -u`
do

  temp=`cat ${INPUT_FILE} | ${JQ} '[.[] | {header: .header.type} ]' | grep ":"  | cut -d':' -f2 | sort | egrep -ce $e`
  name=`echo $e | sed 's/"//g'`
  echo " $name : $temp"

done

#This will get the total weight for the sending types
wt=`cat ${INPUT_FILE} | ${JQ} '[.[] | select(.header.type == "sending") ]' | ${JQ} '[ .[] | .items[].weight]' | ${JQ} add`

echo "Total Weight:$wt"

