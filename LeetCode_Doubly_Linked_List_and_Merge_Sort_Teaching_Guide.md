# LeetCode Teaching Guide: Doubly Linked Lists and Merge Sort

**Audience:** beginner to intermediate DSA students  
**Language:** Java  
**Order:** 707 → 912 → 430 → 148

> The question summaries and examples below are original paraphrases for teaching. Open each linked LeetCode page for the complete official wording, limits, and judge-provided class definitions. Code blocks are independent submissions; paste one into its own problem editor.

## A method for approaching any LeetCode problem

1. **Restate the contract.** Write down the input, required output, method signature, and whether you may change the input. For design questions, list each operation and its exact behavior.
2. **Work one tiny example by hand.** Include the empty case and one boundary case. Draw nodes with arrows or write array indexes above values.
3. **Identify the key invariant.** An invariant is something that must remain true after every operation: for a doubly linked list, `a.next == b` implies `b.prev == a`; for a merge, the output prefix is sorted.
4. **Choose a pattern.** Linked-list rewiring, depth-first traversal, divide and conquer, or two-pointer merging. Explain why that pattern fits before writing syntax.
5. **Write helper contracts.** Say what a helper receives and returns. For example, `merge(a,b)` returns the head of a sorted list containing all nodes from two sorted lists.
6. **Dry-run pointer or index changes.** Show the value of every moving pointer immediately before and after one iteration. Save references that would otherwise be lost.
7. **Prove the stopping condition.** Every loop and recursive call must make measurable progress. State what happens when one input side runs out.
8. **Analyze time and space separately.** Count traversal or merge work, recursion depth, and any temporary arrays or stacks.
9. **Test adversarial boundaries.** Empty, one item, two items, duplicate values, already sorted, reversed, deletion at head/tail, nested child lists.
10. **Submit, then learn from failures.** Reproduce the failing input locally, locate the first violated invariant, make one targeted correction, and retest.

### A 45-minute classroom routine

| Time | Teacher action | Student action |
|---|---|---|
| 0–5 min | Display only the prompt and signature; ask for input/output | Paraphrase without code |
| 5–12 min | Draw a small case and ask for edge cases | Predict expected result |
| 12–20 min | Ask for invariant and helper contracts | Explain the algorithm aloud |
| 20–30 min | Trace pointers/indexes on board | Complete one trace table |
| 30–40 min | Implement incrementally | Code, run custom cases |
| 40–45 min | Review a deliberate broken case | Diagnose the first broken invariant |

**Prompt students with:** “Which reference will you lose if you overwrite this link?”, “What exactly does this pointer mean?”, “What shrinks in each recursive call?”, and “Which edge case invalidates your current idea?”

---

## 1. LeetCode 707 — Design Linked List

**Official question:** https://leetcode.com/problems/design-linked-list/  
**Level:** introductory pointer implementation (LeetCode: Medium).  
**Practice constraint:** implement a **doubly** linked list with no collection class doing the work.

### Question in our own words

Build a class `MyLinkedList` storing integer nodes. Implement `get(index)` (value or `-1` for an invalid index), `addAtHead(val)`, `addAtTail(val)`, `addAtIndex(index,val)`, and `deleteAtIndex(index)`. Insertion at index 0 is at the head; insertion at the current size appends; insertion beyond size does nothing. A negative insertion index acts as 0. Invalid deletion does nothing. Indexing starts at 0. The judge constructs the object and calls these methods in sequence; the output of one operation depends on earlier operations.

**Example:** add head 1; add tail 3; add at index 1 value 2 → `1 ⇄ 2 ⇄ 3`; `get(1)` returns 2; delete index 1 → `1 ⇄ 3`.

### Design before coding

Keep `head`, `tail`, and `size`. Each node stores `val`, `prev`, `next`. Invariant: `head.prev == null`, `tail.next == null`, and every adjacent pair has links in both directions. Find the node at an index from the closer end. For insertion, identify predecessor `before` and successor `after`, and set all four affected links. For deletion, reconnect neighbors, including null boundaries.

### Java solution (paste into problem 707)

```java
class MyLinkedList {
    private static class Node {
        int val;
        Node prev, next;
        Node(int val) { this.val = val; }
    }

    private Node head, tail;
    private int size;

    private Node nodeAt(int index) {
        if (index < size / 2) {
            Node p = head;
            for (int i = 0; i < index; i++) p = p.next;
            return p;
        }
        Node p = tail;
        for (int i = size - 1; i > index; i--) p = p.prev;
        return p;
    }

    public int get(int index) {
        if (index < 0 || index >= size) return -1;
        return nodeAt(index).val;
    }

    public void addAtHead(int val) { addAtIndex(0, val); }
    public void addAtTail(int val) { addAtIndex(size, val); }

    public void addAtIndex(int index, int val) {
        if (index > size) return;
        if (index < 0) index = 0;
        Node after = index == size ? null : nodeAt(index);
        Node before = after == null ? tail : after.prev;
        Node fresh = new Node(val);
        fresh.prev = before;
        fresh.next = after;
        if (before == null) head = fresh;
        else before.next = fresh;
        if (after == null) tail = fresh;
        else after.prev = fresh;
        size++;
    }

    public void deleteAtIndex(int index) {
        if (index < 0 || index >= size) return;
        Node victim = nodeAt(index);
        Node before = victim.prev, after = victim.next;
        if (before == null) head = after;
        else before.next = after;
        if (after == null) tail = before;
        else after.prev = before;
        size--;
    }
}
```

**Dry run:** Starting `1 ⇄ 3`, inserting 2 at index 1 gives `before=1`, `after=3`; link `1.next=2`, `2.prev=1`, `2.next=3`, `3.prev=2`. Deleting index 1 reconnects `1.next=3` and `3.prev=1`.

**Complexity:** `get`, indexed insert/delete O(n) worst case; add at head/tail O(1); O(n) storage. `nodeAt` traverses from whichever end is nearer.

**Teaching trap:** Setting `before.next` but forgetting `after.prev` may leave forward traversal apparently correct while backward traversal is broken. Ask students to trace both directions.

**Try:** empty `get(0)`; insert at `-2`; append at `size`; insert at `size+1`; delete only node; delete head and tail.

---

## 2. LeetCode 912 — Sort an Array

**Official question:** https://leetcode.com/problems/sort-an-array/  
**Level:** first merge-sort implementation (LeetCode: Medium).

### Question in our own words

Given an integer array, return its values in ascending order. Do not use a built-in sort. The target running time is O(n log n); this lesson deliberately uses merge sort. The prompt also asks for low space usage: conventional merge sort uses O(n) extra storage, so it meets the time goal but is not an in-place or minimum-space solution.

**Example:** `[5,2,3,1]` → `[1,2,3,5]`.

### Design before coding

`sort(lo,hi)` sorts the inclusive range. Base case: `lo >= hi`. Sort both halves before merging them. During `merge`, `[lo,i)` and `[mid+1,j)` have already been consumed; temporary output `[lo,k)` is sorted. Reuse one temporary array across recursive calls.

### Java solution (paste into problem 912)

```java
class Solution {
    public int[] sortArray(int[] nums) {
        int[] temp = new int[nums.length];
        sort(nums, temp, 0, nums.length - 1);
        return nums;
    }

    private void sort(int[] a, int[] tmp, int lo, int hi) {
        if (lo >= hi) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, tmp, lo, mid);
        sort(a, tmp, mid + 1, hi);
        merge(a, tmp, lo, mid, hi);
    }

    private void merge(int[] a, int[] tmp, int lo, int mid, int hi) {
        int i = lo, j = mid + 1, k = lo;
        while (i <= mid && j <= hi) {
            if (a[i] <= a[j]) tmp[k++] = a[i++];
            else tmp[k++] = a[j++];
        }
        while (i <= mid) tmp[k++] = a[i++];
        while (j <= hi) tmp[k++] = a[j++];
        for (int p = lo; p <= hi; p++) a[p] = tmp[p];
    }
}
```

**Dry run:** `[5,2,3,1]` divides into `[5,2]` and `[3,1]`; these merge to `[2,5]` and `[1,3]`. Final merge chooses 1, 2, 3, then copies remaining 5. During this final merge `i` points into the left half, `j` into the right half, and `k` to the next output slot.

**Complexity:** O(n log n) time, O(n) temporary array plus O(log n) recursion stack; O(n) auxiliary space overall.

**Teaching trap:** `merge` assumes its two halves are already sorted. Pause execution after the recursive calls and ask students to justify that assumption. `<=` takes a left element first on ties, preserving stability in this implementation.

**Try:** `[]`, `[4]`, `[2,2,1]`, sorted and reverse-sorted arrays, negative values.

---

## 3. LeetCode 430 — Flatten a Multilevel Doubly Linked List

**Official question:** https://leetcode.com/problems/flatten-a-multilevel-doubly-linked-list/  
**Level:** intermediate pointer rewiring (LeetCode: Medium).

### Question in our own words

Nodes have `prev`, `next`, and `child`. A child may itself head a doubly linked list with further children. Flatten the structure into one doubly linked list in depth-first order: after a node, visit its entire child list before returning to that node's former next node. Return the original head. Every `child` reference in the result must be null, and every forward/backward link must agree. LeetCode supplies the `Node` class; use its editor definition.

**Example:** `1 ⇄ 2 ⇄ 3`, where 2 has child `7 ⇄ 8`, becomes `1 ⇄ 2 ⇄ 7 ⇄ 8 ⇄ 3`.

### Design before coding

Walk with `current`. On a child, save `next` *before* changing it. Push that saved node onto a stack for later. Attach the child after `current`, set the child’s `prev`, clear `child`, and continue. When the current chain ends, pop the postponed next node and attach it. Stack order ensures a nested child is processed before an outer postponed sibling. Invariant: the traversed prefix is a valid doubly linked list and contains no child pointers.

### Java solution (paste into problem 430)

```java
import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public Node flatten(Node head) {
        if (head == null) return null;
        Deque<Node> postponed = new ArrayDeque<>();
        Node current = head;

        while (current != null) {
            if (current.child != null) {
                if (current.next != null) postponed.push(current.next);
                Node childHead = current.child;
                current.child = null;
                current.next = childHead;
                childHead.prev = current;
            } else if (current.next == null && !postponed.isEmpty()) {
                Node resume = postponed.pop();
                current.next = resume;
                resume.prev = current;
            }
            current = current.next;
        }
        return head;
    }
}
```

**Dry run:** At 2, save 3, connect 2→7, clear `2.child`. Walk 7→8. At 8, pop 3 and connect 8→3. The `prev` pointers must be updated at both connections.

**Complexity:** O(n) time for n total nodes, O(d) stack storage where d is the maximum number of saved continuation nodes (at most O(n)). No recursion stack is used.

**Teaching trap:** Failing to save `current.next` before setting it to the child loses the remainder of the original level. An empty child never gets pushed; a child on the last node needs no saved continuation.

**Try:** null head; no children; child on last node; child whose node also has a child; children on two consecutive nodes. Traverse backward after flattening and confirm each `child == null`.

---

## 4. LeetCode 148 — Sort List

**Official question:** https://leetcode.com/problems/sort-list/  
**Level:** intermediate merge sort on a **singly** linked list (LeetCode: Medium). This problem reinforces merging, but unlike 707/430 its nodes do not have `prev`.

### Question in our own words

Receive the head of a singly linked list and return the head of the same nodes sorted in ascending order. The target time is O(n log n). The official follow-up asks for O(1) auxiliary memory; the top-down recursive solution below uses O(log n) stack space, so it solves the main problem but **does not meet that follow-up**. Iterative bottom-up merge sort can meet the follow-up.

**Example:** `4 → 2 → 1 → 3` → `1 → 2 → 3 → 4`.

### Design before coding

Use slow/fast pointers to locate a midpoint and **cut** `slow.next` so both recursive subproblems strictly shrink. Recursively sort each half. Merge two sorted lists by attaching the smaller current node to an output tail. A dummy node simplifies the first attachment. With `<=`, equal keys from the left half stay ahead of equal keys from the right.

### Java solution (paste into problem 148)

```java
class Solution {
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode right = slow.next;
        slow.next = null;
        ListNode leftSorted = sortList(head);
        ListNode rightSorted = sortList(right);
        return merge(leftSorted, rightSorted);
    }

    private ListNode merge(ListNode a, ListNode b) {
        ListNode dummy = new ListNode(0), tail = dummy;
        while (a != null && b != null) {
            if (a.val <= b.val) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }
        tail.next = a != null ? a : b;
        return dummy.next;
    }
}
```

**Dry run:** `4→2→1→3` splits into `4→2` and `1→3`; then single nodes. Merge `4` and `2` to `2→4`; merge `1` and `3` to `1→3`; finally compare front nodes and form `1→2→3→4`.

**Complexity:** O(n log n) time; O(log n) recursive call stack, O(1) other auxiliary space. Do not call the entire recursive algorithm O(1) space.

**Teaching trap:** Forgetting `slow.next = null` means the left recursive call still contains the original list and may recurse forever. Ask students to show the exact two resulting chains after the cut.

**Try:** null; one node; two reversed nodes; all equal; negatives; an already sorted list.

---

## Comparison and lesson progression

| Problem | Primary idea | Main invariant | Time | Extra space |
|---|---|---|---|---|
| 707 | Doubly linked-list operations | Neighbor links agree | O(n) indexed, O(1) end insertion | O(n) nodes |
| 912 | Array merge sort | Merged output prefix sorted | O(n log n) | O(n) |
| 430 | Depth-first flattening | Processed prefix has valid bidirectional links | O(n) | O(d) stack |
| 148 | Linked-list merge sort | Both merge inputs sorted | O(n log n) | O(log n) stack |

**Suggested assessment:** Give students one unseen edge case per problem. Ask for (1) a hand-drawn trace, (2) an invariant, (3) one line whose removal breaks the solution, and (4) complexity with its source. This measures reasoning beyond memorized code.

## Official questions

- [707 — Design Linked List](https://leetcode.com/problems/design-linked-list/)
- [912 — Sort an Array](https://leetcode.com/problems/sort-an-array/)
- [430 — Flatten a Multilevel Doubly Linked List](https://leetcode.com/problems/flatten-a-multilevel-doubly-linked-list/)
- [148 — Sort List](https://leetcode.com/problems/sort-list/)
