
def print_array(arr, start, slutt):
    if start > slutt:
        return 
    
    mid = (start + slutt) // 2
    
    print(arr[mid])
    print_array(arr, mid + 1, slutt)
    print_array(arr, start, mid - 1)


if __name__ == "__main__":
    import sys
    input_numbers = list(map(int, sys.stdin.read().strip().split()))
    print_array(input_numbers, 0, len(input_numbers) - 1)