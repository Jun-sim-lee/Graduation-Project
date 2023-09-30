import sys
import csv

ap_dict = {}

name_list = ["0,7", "0,8", "1,7", "1,8", "2,7", "2,8", "3,4", "3,5", "3,6", "3,7", "3,8", "3,9", "3,10", "3,11",
             "4,4", "4,11", "5,4", "5,11", "6,4", "6,11", "7,4", "7,11", "8,4", "8,11", "9,4", "9,11", "10,4", "10,11",
             "11,4", "11,11", "12,4", "12,11", "13,4", "13,11", "14,4","14,5","14,6","14,7","14,8","14,9","14,10","14,11"
             ,"15,4","16,4","17,4"]
header = []
BASE_DIRECTORY = "RSS-"

def create_dictionary():
    fp = open("ap_list.txt", 'r')
    
    for line in fp.readlines():
        MAC = line.rstrip()[0:17]
        VALUE = line.rstrip()[18:]
        ap_dict[MAC] = int(VALUE)
    
    fp.close()

def create_header():
    for i in range(134):
        header.append(i)
    header.append("result")

    csv_fp = open("rss_csv.csv", "a")
    writer = csv.writer(csv_fp)
    writer.writerow(header)
    csv_fp.close()

def create_list(outer_idx, coord_idx):
    cnt = 0
    file_list = []
    rss_list = [0 for _ in range(135)]
    CURR_DIRECTORY = BASE_DIRECTORY + str(outer_idx)

    for inner_idx in range(1, 11):
        CURR_FILENAME = CURR_DIRECTORY + "/" + "RSS-1[" + name_list[coord_idx] + "]#" + str(inner_idx) + ".txt"

        fp = open(CURR_FILENAME, 'r')
        csv_fp = open("rss_csv.csv", "a")
        writer = csv.writer(csv_fp)

        for line in fp.readlines():
            file_list.append(line.rstrip())

        while cnt < len(file_list):
            rss_list[ap_dict[file_list[cnt]]] = int(file_list[cnt+1])
            cnt += 2
        
        splitted_name = name_list[coord_idx].split(',')
        rss_list[134] = splitted_name[0] + splitted_name[1]
        writer.writerows([rss_list])
    
    csv_fp.close()
    fp.close()

sys.path.append("../")

create_dictionary()
create_header()

for outer_idx in range(1, 5):
    for coord_idx in range(len(name_list)):
        create_list(outer_idx, coord_idx)