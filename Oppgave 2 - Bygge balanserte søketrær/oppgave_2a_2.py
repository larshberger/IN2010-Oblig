
def print_array(arr, start, slutt):
    if start > slutt:
        return []
    
    # finner median
    mid = (start + slutt) // 2
    current = [arr[mid]]
    
    # jobber rekursivt med hver sin liste
    h = print_array(arr, mid + 1, slutt)
    v = print_array(arr, start, mid - 1)

    # legger sammen listene
    return current + v + h

if __name__ == "__main__":
    import sys
    if len(sys.argv) > 1:
            # Lesing fra fil
            filnavn = sys.argv[1]
            with open(filnavn, 'r') as f:
                input_nummer = list(map(int, f.read().strip().split()))
    else:
            # Lesing fra stdin
            input_nummer = list(map(int, sys.stdin.read().strip().split()))
    
    balansert = print_array(input_nummer, 0, len(input_nummer) - 1)
 
    for nummer in balansert:
        print(nummer)


