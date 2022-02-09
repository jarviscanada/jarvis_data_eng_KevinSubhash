#! /bin/sh

hostname=$(hostname -f)

#save number of CPU to a variable
lscpu_out=`lscpu`

#usage
memory_free=$(echo `cat /proc/meminfo` | egrep "MemFree:" | awk '{print ($5/1000)}' | xargs)
cpu_idle=$(echo `vmstat --unit M` | awk '{print $38}' | xargs)
cpu_kernel=$(echo `vmstat --unit M` | awk '{print $37}' | xargs)
disk_io=$(echo `vmstat -d` | awk '{print $24}' | xargs)
disk_available=$(echo `df -BM` | awk '{print substr($11, 1, length($11)-1)}' | xargs)