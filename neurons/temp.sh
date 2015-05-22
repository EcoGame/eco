for file in *.txt.ann.ann
do
 mv "$file" "${file%.txt.ann.ann}.ann"
done
