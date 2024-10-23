import heapq
import sys

def pri_koo(pk):
    koo = []

    heapq.heappush(koo, (0, len(pk) - 1))

    while koo:
        start, slutt = heapq.heappop(koo)

        if start > slutt:
            continue

        mid = (start + slutt) // 2
      
        print(pk[mid])

        heapq.heappush(koo, (start, mid-1))
        heapq.heappush(koo, (mid + 1, slutt))

if __name__ == "__main__":
    if len(sys.argv) > 1:
            # Lesing fra fil
            filnavn = sys.argv[1]
            with open(filnavn, 'r') as f:
                pk = list(map(int, f.read().strip().split()))
    else:
            # Lesing fra stdin
            pk = list(map(int, sys.stdin.read().strip().split()))
    
    heapq.heapify(pk)
    pri_koo(pk)