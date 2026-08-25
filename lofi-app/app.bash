#! /usr/bin/env bash

if [[ $IMPORTANT_VARIABLE == "true" ]]
then
  echo "All systems go!"
else
  if [[ $NOW_WITH_LOGS == "true" ]]
  then echo "Error: Missing variable \"IMPORTANT_VARIABLE\". Allowed values: \"true\"."
  else echo "Error!"
  fi
  exit 1
fi


# 100% uptime!
sleep infinity
