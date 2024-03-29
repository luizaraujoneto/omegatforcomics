#!/usr/bin/env bash
#
#  OmegaT - Computer Assisted Translation (CAT) tool
#           with fuzzy matching, translation memory, keyword search,
#           glossaries, and translation leveraging into updated projects.
#
#  Copyright (C) 2023 Hiroshi Miura
#                Home page: https://www.omegat.org/
#                Support center: https://omegat.org/support
#
#  This file is part of OmegaT.
#
#  OmegaT is free software: you can redistribute it and/or modify
#  it under the terms of the GNU General Public License as published by
#  the Free Software Foundation, either version 3 of the License, or
#  (at your option) any later version.
#
#  OmegaT is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#  GNU General Public License for more details.
#
#  You should have received a copy of the GNU General Public License
#  along with this program.  If not, see <https://www.gnu.org/licenses/>.
#

if [ "$#" -ne 2 ]; then
  echo "Illegal number of arguments. Please run with arguments" >&2
  echo "$0 (GIT|SVN) DURATION " >&2
  exit 2
fi

# should run on the project root directory
SHELL_PATH=`dirname "$0"`
cd $SHELL_PATH && cd ..

EXIT_CODE=0
export DURATION=$2
export TYPE=$1

# check compose V1 then V2, or containerd/nerdctl
if type docker-compose &> /dev/null ; then
  docker-compose -f compose.yml up -d server
  docker-compose -f compose.yml up client || EXIT_CODE=$?
  docker-compose -f compose.yml down
elif type docker &> /dev/null ; then
  docker compose -f compose.yml up -d server
  docker compose -f compose.yml up client || EXIT_CODE=$?
  docker compose -f compose.yml down
elif type nerdctl &> /dev/null ; then
  nerdctl compose -f compose.yml up -d server
  nerdctl compose -f compose.yml up client || EXIT_CODE=$?
  nerdctl compose -f compose.yml down
else
  echo Please install docker or nerdctl!
  EXIT_CODE=2
fi
exit $EXIT_CODE
