#!/bin/sh
dir=$PWD
cd $dir
rm -rf output/*
cd Installer/InstallJammer
./installjammer -DVersion "$1" -Dfiles "$dir" -Djarfile "$dir/target" -Dcommon "$dir/Installer/common_files" -Dscripts "$dir/Installer/scripts" -Ddb "$dir/Installer/db" --output-dir "$dir/output" --platform "Linux-x86_64" --build "$dir/Finley-Monitor-x64.mpi"
